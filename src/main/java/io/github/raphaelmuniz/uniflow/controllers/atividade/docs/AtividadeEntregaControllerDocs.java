package io.github.raphaelmuniz.uniflow.controllers.atividade.docs;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeEntregaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeEntregaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AvaliacaoAtividadeResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
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

@Tag(name = "Entregas de Atividades", description = "Endpoints para gerenciar a entrega e avaliação de atividades dos alunos")
public interface AtividadeEntregaControllerDocs {

    @Operation(summary = "Busca detalhes de uma entrega de atividade", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AtividadeEntregaDetalhadaResponseDTO.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Entrega não encontrada", content = @Content)
    ResponseEntity<AtividadeEntregaDetalhadaResponseDTO> buscarPorId(@Parameter(description = "ID da entrega a ser buscada") @PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Realiza a entrega de uma atividade (Aluno)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Atividade entregue com sucesso", content = @Content)
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'ATIVIDADE_ENTREGA_EDITAR' necessária)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Entrega não encontrada", content = @Content)
    ResponseEntity<Void> entregarAtividade(@Parameter(description = "ID da entrega a ser submetida") @PathVariable String id, @RequestBody @Valid AtividadeEntregaRequestDTO dto, @AuthenticationPrincipal Estudante estudanteLogado);

    @Operation(summary = "Avalia a entrega de uma atividade (Professor)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Avaliação registrada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AvaliacaoAtividadeResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'ATIVIDADE_ENTREGA_AVALIAR' necessária)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Entrega não encontrada", content = @Content)
    ResponseEntity<AvaliacaoAtividadeResponseDTO> avaliarEntrega(@Parameter(description = "ID da entrega a ser avaliada") @PathVariable String id, @RequestBody @Valid AvaliacaoRequestDTO dto, @AuthenticationPrincipal Professor professorLogado);
}