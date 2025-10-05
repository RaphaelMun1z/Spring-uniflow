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
import java.util.stream.Collectors;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token.expire-length:3600000}")
    private long validityInMilliseconds;

    @Value("${security.jwt.token.refresh-expire-length:10800000}")
    private long refreshValidityInMilliseconds;

    private Algorithm algorithm = null;

    public JwtTokenProvider() {
    }

    @PostConstruct
    protected void init() {
        algorithm = Algorithm.HMAC256(secretKey.getBytes());
    }

    public TokenDTO criarTokenDTO(Usuario usuario) {
        Date agora = new Date();
        Date validadeAccessToken = new Date(agora.getTime() + validityInMilliseconds);
        Date validadeRefreshToken = new Date(agora.getTime() + refreshValidityInMilliseconds);

        List<String> authorities = usuario.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        String accessToken = criarAccessToken(usuario.getEmail(), authorities, agora, validadeAccessToken);
        String refreshToken = criarRefreshToken(usuario.getEmail(), agora, validadeRefreshToken);

        return new TokenDTO(usuario.getUsername(), true, agora, validadeAccessToken, accessToken, refreshToken);
    }

    public TokenDTO atualizarToken(String refreshToken, Usuario usuario) {
        String token = resolverToken(refreshToken);
        if (token == null) {
            throw new InvalidJwtAuthenticationException("Refresh token inválido ou ausente.");
        }

        DecodedJWT decodedJWT = decodedToken(token);

        if (!decodedJWT.getSubject().equals(usuario.getEmail())) {
            throw new InvalidJwtAuthenticationException("Refresh token não pertence ao usuário informado.");
        }

        Date agora = new Date();
        Date validadeAccessToken = new Date(agora.getTime() + validityInMilliseconds);
        List<String> authorities = usuario.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

        String novoAccessToken = criarAccessToken(usuario.getEmail(), authorities, agora, validadeAccessToken);

        return new TokenDTO(usuario.getUsername(), true, agora, validadeAccessToken, novoAccessToken, token);
    }

    public Authentication obterAutenticacao(String token) {
        DecodedJWT decodedJWT = decodedToken(token);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        if (roles == null) {
            roles = new ArrayList<>();
        }

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

    public String resolverToken(HttpServletRequest request) {
        return resolverToken(request.getHeader("Authorization"));
    }

    public boolean validarToken(String token) {
        try {
            DecodedJWT decodedJWT = decodedToken(token);
            return !decodedJWT.getExpiresAt().before(new Date());
        } catch (Exception e) {
            throw new InvalidJwtAuthenticationException("Token expirado ou inválido!");
        }
    }

    private String resolverToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private String criarAccessToken(String username, List<String> authorities, Date now, Date validity) {
        return JWT.create()
            .withClaim("roles", authorities)
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .sign(algorithm);
    }

    private String criarRefreshToken(String username, Date now, Date validity) {
        return JWT.create()
            .withIssuedAt(now)
            .withExpiresAt(validity)
            .withSubject(username)
            .sign(algorithm);
    }

    private DecodedJWT decodedToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}