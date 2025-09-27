package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.repositories.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UsuarioRepository repository;

    public ResponseEntity<TokenDTO> signin(AccountCredentialsDTO credentials){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword()
            )
        );

        var user = repository.findByEmail(credentials.getUsername());
        if(user.isEmpty()) {
            throw new UsernameNotFoundException("Username " + credentials.getUsername() + " not found!");
        }

        var token = tokenProvider.createAcessToken(
                credentials.getUsername(),
                user.get().getRoles()
        );

        return ResponseEntity.ok(token);
    }
}
