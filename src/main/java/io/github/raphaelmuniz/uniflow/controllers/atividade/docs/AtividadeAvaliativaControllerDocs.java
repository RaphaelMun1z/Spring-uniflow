package io.github.raphaelmuniz.uniflow.controllers.atividade.docs;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
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

@Tag(name = "Atividades Avaliativas", description = "Endpoints para gerenciar o ciclo de vida de uma Atividade Avaliativa")
public interface AtividadeAvaliativaControllerDocs {

    @Operation(
        summary = "Busca detalhes de uma atividade avaliativa por ID",
        description = "Retorna os detalhes completos de uma atividade, incluindo as entregas dos alunos. Acessível pelo professor criador e pelos alunos da turma.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
        responseCode = "200",
        description = "Busca realizada com sucesso",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AtividadeAvaliativaDetalhadaResponseDTO.class))
    )
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Atividade não encontrada", content = @Content)
    ResponseEntity<AtividadeAvaliativaDetalhadaResponseDTO> buscarPorId(
        @Parameter(description = "ID da atividade a ser buscada") @PathVariable String id,
        @AuthenticationPrincipal Usuario usuarioLogado
    );

    @Operation(
        summary = "Atualiza uma atividade avaliativa existente (Professor)",
        description = "Permite que o professor criador da atividade altere seus detalhes, como título, descrição e prazo.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(
        responseCode = "200",
        description = "Atividade atualizada com sucesso",
        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AtividadeAvaliativaResponseDTO.class))
    )
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'ATIVIDADE_AVALIATIVA_EDITAR' necessária)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Atividade não encontrada", content = @Content)
    ResponseEntity<AtividadeAvaliativaResponseDTO> atualizar(
        @Parameter(description = "ID da atividade a ser atualizada") @PathVariable String id,
        @RequestBody @Valid AtividadeAvaliativaUpdateRequestDTO dto,
        @AuthenticationPrincipal Usuario professorLogado
    );

    @Operation(
        summary = "Deleta uma atividade avaliativa (Professor)",
        description = "Permite que o professor criador delete uma atividade. Esta ação removerá em cascata todas as entregas dos alunos associadas.",
        security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "204", description = "Atividade deletada com sucesso", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'ATIVIDADE_AVALIATIVA_DELETAR' necessária)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Atividade não encontrada", content = @Content)
    ResponseEntity<Void> deletar(
        @Parameter(description = "ID da atividade a ser deletada") @PathVariable String id,
        @AuthenticationPrincipal Usuario professorLogado
    );
}