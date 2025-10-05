package io.github.raphaelmuniz.uniflow.controllers.notificacao.docs;

import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoBroadcastRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
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

@Tag(name = "Admin - Notificações", description = "Endpoints administrativos para criar e enviar notificações")
public interface NotificacaoControllerDocs {

    @Operation(summary = "Envia uma notificação para todos os membros de um grupo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Notificação enviada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotificacaoResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'NOTIFICACAO_CRIAR' necessária)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado", content = @Content)
    ResponseEntity<NotificacaoResponseDTO> enviarParaGrupo(@RequestBody @Valid NotificacaoGrupoRequestDTO dto, @AuthenticationPrincipal Usuario remetente);

    @Operation(summary = "Envia uma notificação para todos os usuários do sistema (broadcast)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Notificação enviada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = NotificacaoResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'NOTIFICACAO_CRIAR' necessária)", content = @Content)
    ResponseEntity<NotificacaoResponseDTO> enviarBroadcast(@RequestBody @Valid NotificacaoBroadcastRequestDTO dto, @AuthenticationPrincipal Usuario remetente);

    @Operation(summary = "Lista todas as notificações enviadas (auditoria)", description = "Recurso administrativo para listar todas as notificações já criadas pelo sistema, com paginação.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'NOTIFICACAO_LER' necessária)", content = @Content)
    ResponseEntity<PaginatedResponse<NotificacaoResponseDTO>> buscarTodas(Pageable pageable);
}