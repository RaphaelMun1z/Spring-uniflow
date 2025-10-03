package io.github.raphaelmuniz.uniflow.controllers.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.EsquecerSenhaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.RedefinirSenhaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.SignUpRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.services.autorizacao.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(@RequestBody @Valid AccountCredentialsDTO credentials) {
        return ResponseEntity.ok(authService.signin(credentials));
    }

    @PutMapping("/refresh/{email}")
    public ResponseEntity<TokenDTO> refreshToken(
            @PathVariable("email") String email,
            @RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(authService.signin(email, refreshToken));
    }

    @PostMapping("/signup")
    public ResponseEntity<AssinanteResponseDTO> signup(@RequestBody @Valid SignUpRequestDTO dto) {
        AssinanteResponseDTO novoUsuario = authService.signup(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> esquecerSenha(@RequestBody @Valid EsquecerSenhaRequestDTO dto) {
        authService.esquecerSenha(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> redefinirSenha(@RequestBody @Valid RedefinirSenhaRequestDTO dto) {
        authService.redefinirSenha(dto);
        return ResponseEntity.ok().build();
    }
}
