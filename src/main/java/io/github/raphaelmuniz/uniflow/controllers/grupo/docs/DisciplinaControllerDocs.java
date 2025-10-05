package io.github.raphaelmuniz.uniflow.controllers.grupo.docs;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.DisciplinaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Disciplinas", description = "Endpoints para gerenciar o catálogo de disciplinas")
public interface DisciplinaControllerDocs {

    @Operation(summary = "Cria uma nova disciplina", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Disciplina criada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DisciplinaResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'DISCIPLINA_CRIAR' necessária)", content = @Content)
    ResponseEntity<DisciplinaResponseDTO> criar(@RequestBody @Valid DisciplinaRequestDTO dto);

    @Operation(summary = "Lista todas as disciplinas disponíveis", description = "Retorna uma lista paginada de todas as disciplinas cadastradas no sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    ResponseEntity<PaginatedResponse<DisciplinaResponseDTO>> buscarTodas(Pageable pageable);

    @Operation(summary = "Busca uma disciplina por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DisciplinaResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada", content = @Content)
    ResponseEntity<DisciplinaResponseDTO> buscarPorId(@Parameter(description = "ID da disciplina a ser buscada") @PathVariable String id);

    @Operation(summary = "Atualiza uma disciplina existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Disciplina atualizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = DisciplinaResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'DISCIPLINA_EDITAR' necessária)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada", content = @Content)
    ResponseEntity<DisciplinaResponseDTO> atualizar(@Parameter(description = "ID da disciplina a ser atualizada") @PathVariable String id, @RequestBody @Valid DisciplinaRequestDTO dto);

    @Operation(summary = "Deleta uma disciplina", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Disciplina deletada com sucesso", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'DISCIPLINA_DELETAR' necessária)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Disciplina não encontrada", content = @Content)
    ResponseEntity<Void> deletar(@Parameter(description = "ID da disciplina a ser deletada") @PathVariable String id);
}