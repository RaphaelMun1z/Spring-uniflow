package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.ProfileUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.ProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Meu Perfil", description = "Endpoints para o usuário logado gerenciar seus próprios dados")
public interface ProfileControllerDocs {

    @Operation(summary = "Busca os dados completos do perfil do usuário logado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProfileResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    ResponseEntity<ProfileResponseDTO> obterMeuPerfil(@AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Atualiza as informações do perfil do usuário logado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProfileResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    ResponseEntity<ProfileResponseDTO> atualizarMeuPerfil(@AuthenticationPrincipal Usuario usuarioLogado, @RequestBody @Valid ProfileUpdateRequestDTO dto);

    @Operation(summary = "Lista o histórico de assinaturas do usuário logado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    ResponseEntity<PaginatedResponse<AssinaturaProfileResponseDTO>> obterMinhasAssinaturas(@AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable);

    @Operation(summary = "Lista o histórico de pagamentos do usuário logado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    ResponseEntity<PaginatedResponse<PagamentoResponseDTO>> obterMeusPagamentos(@AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable);

    @Operation(summary = "Lista os grupos dos quais o usuário logado faz parte", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    ResponseEntity<PaginatedResponse<GruposProfileResponseDTO>> obterMeusGrupos(@AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable);

    @Operation(summary = "Cancela a conta do usuário logado", description = "Ação irreversível que remove o usuário e seus dados associados.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Conta cancelada com sucesso", content = @Content)
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    ResponseEntity<Void> cancelarMinhaConta(@AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Cancela a assinatura ativa do usuário logado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Assinatura cancelada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssinaturaUsuarioResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Nenhuma assinatura ativa encontrada para cancelar", content = @Content)
    ResponseEntity<AssinaturaUsuarioResponseDTO> cancelarMinhaAssinatura(@AuthenticationPrincipal Usuario usuarioLogado);
}