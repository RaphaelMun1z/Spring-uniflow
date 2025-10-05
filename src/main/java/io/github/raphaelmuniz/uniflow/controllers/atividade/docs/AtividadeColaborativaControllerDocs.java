package io.github.raphaelmuniz.uniflow.controllers.atividade.docs;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeColaborativaUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.TarefaStatusUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeColaborativaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Atividades Colaborativas", description = "Endpoints para gerenciar o ciclo de vida de uma Atividade Colaborativa")
public interface AtividadeColaborativaControllerDocs {

    @Operation(summary = "Busca detalhes de uma atividade colaborativa", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AtividadeColaborativaDetalhadaResponseDTO.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Atividade não encontrada", content = @Content)
    ResponseEntity<AtividadeColaborativaDetalhadaResponseDTO> buscarPorId(@Parameter(description = "ID da atividade a ser buscada") @PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Atualiza uma atividade colaborativa", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AtividadeColaborativaDetalhadaResponseDTO.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Atividade não encontrada", content = @Content)
    ResponseEntity<AtividadeColaborativaDetalhadaResponseDTO> atualizar(@Parameter(description = "ID da atividade a ser atualizada") @PathVariable String id, @RequestBody @Valid AtividadeColaborativaUpdateRequestDTO dto, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Deleta uma atividade colaborativa", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Atividade deletada com sucesso", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Atividade não encontrada", content = @Content)
    ResponseEntity<Void> deletar(@Parameter(description = "ID da atividade a ser deletada") @PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Atualiza o status de um membro em uma atividade", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Status atualizado com sucesso", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Tarefa do membro não encontrada", content = @Content)
    ResponseEntity<Void> atualizarMeuStatusNaAtividade(@Parameter(description = "ID da atividade colaborativa") @PathVariable("id") String atividadeId, @RequestBody @Valid TarefaStatusUpdateRequestDTO dto, @AuthenticationPrincipal Usuario usuarioLogado);
}