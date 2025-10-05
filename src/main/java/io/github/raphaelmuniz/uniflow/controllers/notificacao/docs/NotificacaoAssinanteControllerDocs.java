package io.github.raphaelmuniz.uniflow.controllers.notificacao.docs;

import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Meu Perfil - Notificações", description = "Endpoints para o usuário logado gerenciar suas próprias notificações")
public interface NotificacaoAssinanteControllerDocs {

    @Operation(summary = "Lista as notificações do usuário logado", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    ResponseEntity<PaginatedResponse<NotificacaoResponseDTO>> buscarMinhasNotificacoes(@AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable);

    @Operation(summary = "Marca uma notificação específica como lida", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Notificação marcada como lida com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotificacaoResponseDTO.class)))
    @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para acessar esta notificação", content = @Content)
    @ApiResponse(responseCode = "404", description = "Notificação não encontrada", content = @Content)
    ResponseEntity<NotificacaoResponseDTO> marcarComoLida(@Parameter(description = "ID da notificação a ser marcada como lida") @PathVariable("id") String notificacaoId, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Marca todas as notificações do usuário como lidas", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Todas as notificações foram marcadas como lidas")
    ResponseEntity<Void> marcarTodasComoLidas(@AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Deleta uma notificação específica", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Notificação deletada com sucesso", content = @Content)
    @ApiResponse(responseCode = "403", description = "Usuário não tem permissão para deletar esta notificação", content = @Content)
    @ApiResponse(responseCode = "404", description = "Notificação não encontrada", content = @Content)
    ResponseEntity<Void> deletarNotificacao(@Parameter(description = "ID da notificação a ser deletada") @PathVariable("id") String notificacaoId, @AuthenticationPrincipal Usuario usuarioLogado);
}