package io.github.raphaelmuniz.uniflow.controllers.grupo.docs;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.AlterarPapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.SubGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.CodigoConviteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Turmas", description = "Endpoints para gerenciar funcionalidades específicas de Turmas")
public interface TurmaControllerDocs {

    @Operation(summary = "Cria uma nova atividade avaliativa em uma turma", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Atividade criada e distribuída com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    ResponseEntity<AtividadeAvaliativaResponseDTO> criarAtividadeAvaliativa(@Parameter(description = "ID da turma") @PathVariable("id") String turmaId, @RequestBody @Valid AtividadeAvaliativaRequestDTO dto, @AuthenticationPrincipal Usuario professorLogado);

    @Operation(summary = "Cria um subgrupo dentro de uma turma", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Subgrupo criado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    ResponseEntity<GrupoResponseDTO> criarSubGrupo(@Parameter(description = "ID da turma pai") @PathVariable("id") String turmaId, @RequestBody @Valid SubGrupoRequestDTO dto, @AuthenticationPrincipal Usuario professorLogado);

    @Operation(summary = "Altera o papel de um membro dentro de uma turma", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Papel do membro alterado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Turma ou membro não encontrado")
    ResponseEntity<Void> alterarPapelMembro(@Parameter(description = "ID da turma") @PathVariable("id") String turmaId, @Parameter(description = "ID do membro a ter o papel alterado") @PathVariable String membroId, @RequestBody @Valid AlterarPapelRequestDTO dto, @AuthenticationPrincipal Usuario professorLogado);

    @Operation(summary = "Busca o código de convite de uma turma", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Turma não encontrada")
    ResponseEntity<CodigoConviteResponseDTO> buscarCodigoConvite(@Parameter(description = "ID da turma") @PathVariable("id") String turmaId, @AuthenticationPrincipal Usuario professorLogado);
}