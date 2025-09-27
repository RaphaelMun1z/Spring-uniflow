package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.services.AuthService;
import jakarta.validation.Valid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthService service;

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(@RequestBody @Valid AccountCredentialsDTO credentials) {
        return ResponseEntity.ok(service.signin(credentials));
    }
}
