package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.repositories.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public TokenDTO signin(AccountCredentialsDTO credentials) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var user = (Usuario) auth.getPrincipal();

            return tokenProvider.createAccessToken(
                    user.getEmail(),
                    user.getAuthorities()
            );
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    public TokenDTO signin(String email, String refreshToken) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("E-mail " + email + " não encontrado!");
        }

        return tokenProvider.refreshToken(refreshToken);
    }
}
