package io.github.raphaelmuniz.uniflow.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.InvalidJwtAuthenticationException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private final long validityInMilliseconds = 3600000;

    @Value("${security.jwt.token.refresh-expire-length:10800000}")
    private final long refreshValidityInMilliseconds = 10800000;

    Algorithm algorithm = null;

    private final Set<String> tokenBlocklist = ConcurrentHashMap.newKeySet();

    public JwtTokenProvider() {
    }

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO createTokenDTO(Usuario usuario) {
        Date now = new Date();
        Date accessTokenValidity = new Date(now.getTime() + validityInMilliseconds);
        Date refreshTokenValidity = new Date(now.getTime() + refreshValidityInMilliseconds);

        List<String> authorities = usuario.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        String accessToken = createAccessToken(usuario.getEmail(), authorities, now, accessTokenValidity);
        String refreshToken = createRefreshToken(usuario.getEmail(), now, refreshTokenValidity);

        return new TokenDTO(usuario.getUsername(), true, now, accessTokenValidity, accessToken, refreshToken);
    }

    public TokenDTO refreshToken(String refreshToken, Usuario usuario) {
        String token = resolveToken(refreshToken);
        if (token == null) {
            throw new InvalidJwtAuthenticationException("Refresh token inválido ou ausente.");
        }

        DecodedJWT decodedJWT = decodedToken(token);

        if (!decodedJWT.getSubject().equals(usuario.getEmail())) {
            throw new InvalidJwtAuthenticationException("Refresh token não pertence ao usuário informado.");
        }

        Date now = new Date();
        Date accessTokenValidity = new Date(now.getTime() + validityInMilliseconds);
        List<String> authorities = usuario.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        String newAccessToken = createAccessToken(usuario.getEmail(), authorities, now, accessTokenValidity);

        return new TokenDTO(usuario.getUsername(), true, now, accessTokenValidity, newAccessToken, token);
    }

    public Authentication getAuthentication(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        List<SimpleGrantedAuthority> authorities = roles.stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());

        var userDetails = org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password("")
            .authorities(authorities)
            .build();

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    public String resolveToken(HttpServletRequest request) {
        return resolveToken(request.getHeader("Authorization"));
    }

    private String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        if (tokenBlocklist.contains(token)) {
            throw new InvalidJwtAuthenticationException("Token has been invalidated by logout!");
        }
        try {
            DecodedJWT decodedJWT = decodedToken(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token!");
        }
    }

    private String createAccessToken(String username, List<String> authorities, Date now, Date validity) {
        return JWT.create()
            .withClaim("roles", authorities)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .sign(algorithm);
    }

    private String createRefreshToken(String username, Date now, Date validity) {
        return JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .sign(algorithm);
    }

    private DecodedJWT decodedToken(String token) {
        Algorithm alg = Algorithm.HMAC256(secretKey.getBytes());
        JWTVerifier verifier = JWT.require(alg).build();
        return verifier.verify(token);
    }
}
