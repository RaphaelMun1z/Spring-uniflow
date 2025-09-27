package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.entities.Usuario;
import io.github.raphaelmuniz.uniflow.repositories.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.security.jwt.JwtTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public TokenDTO signin(AccountCredentialsDTO credentials) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var user = (Usuario) auth.getPrincipal();

            TokenDTO token = tokenProvider.createAcessToken(
                    user.getEmail(),
                    user.getRoles()
            );

            return token;
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }
}
