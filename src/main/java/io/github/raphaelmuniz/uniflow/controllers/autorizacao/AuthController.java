package io.github.raphaelmuniz.uniflow.controllers.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.services.autorizacao.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(@RequestBody @Valid AccountCredentialsDTO credentials) {
        return ResponseEntity.ok(service.signin(credentials));
    }

    @PutMapping("/refresh/{email}")
    public ResponseEntity<TokenDTO> refreshToken(
            @PathVariable("email") String email,
            @RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(service.signin(email, refreshToken));
    }
}
