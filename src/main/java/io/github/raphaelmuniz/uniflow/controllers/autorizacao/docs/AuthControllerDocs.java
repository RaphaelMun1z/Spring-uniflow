package io.github.raphaelmuniz.uniflow.controllers.autorizacao.docs;

import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.EsquecerSenhaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.RedefinirSenhaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.SignUpRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@Tag(name = "Autenticação", description = "Endpoints para autenticação, registro e gerenciamento de sessão")
public interface AuthControllerDocs {

    @Operation(summary = "Autentica um usuário e retorna um token")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenDTO.class)))
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content)
    ResponseEntity<TokenDTO> signin(@RequestBody @Valid AccountCredentialsDTO credentials);

    @Operation(summary = "Atualiza um access token usando um refresh token")
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenDTO.class)))
    @ApiResponse(responseCode = "403", description = "Refresh token inválido ou expirado", content = @Content)
    ResponseEntity<TokenDTO> refreshToken(@Parameter(description = "Email do usuário") @PathVariable("email") String email, @Parameter(description = "Refresh token com o prefixo 'Bearer '") @RequestHeader("Authorization") String refreshToken);

    @Operation(summary = "Registra um novo usuário (Estudante ou Professor)")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssinanteResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos (ex: email já em uso)", content = @Content)
    ResponseEntity<AssinanteResponseDTO> signup(@RequestBody @Valid SignUpRequestDTO dto);

    @Operation(summary = "Inicia o processo de recuperação de senha")
    @ApiResponse(responseCode = "200", description = "Requisição processada. Se o email existir, um link de redefinição será enviado.")
    @ApiResponse(responseCode = "400", description = "Email com formato inválido", content = @Content)
    ResponseEntity<Void> esquecerSenha(@RequestBody @Valid EsquecerSenhaRequestDTO dto);

    @Operation(summary = "Conclui o processo de recuperação de senha")
    @ApiResponse(responseCode = "200", description = "Senha redefinida com sucesso.")
    @ApiResponse(responseCode = "400", description = "Token inválido, expirado ou nova senha inválida", content = @Content)
    ResponseEntity<Void> redefinirSenha(@RequestBody @Valid RedefinirSenhaRequestDTO dto);
}