package io.github.raphaelmuniz.uniflow.controllers.grupo.docs;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.*;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.AtividadeDoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.MembroGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Grupos", description = "Endpoints para criar e gerenciar grupos, turmas e seus membros/atividades")
public interface GrupoControllerDocs {

    @Operation(summary = "Cria um novo grupo (Turma ou Grupo de Estudo)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Grupo criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou violação de regras de negócio")
    @ApiResponse(responseCode = "403", description = "Usuário não tem permissão de plano para criar o grupo")
    ResponseEntity<GrupoResponseDTO> criarGrupo(@RequestBody @Valid GrupoRequestDTO grupoRequestDTO, @AuthenticationPrincipal Usuario usuarioAutenticado);

    @Operation(summary = "Lista todos os grupos (paginado)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    ResponseEntity<PaginatedResponse<GrupoResponseDTO>> buscarTodos(Pageable pageable);

    @Operation(summary = "Busca um grupo por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Usuário não é membro do grupo (se for privado)")
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ResponseEntity<GrupoResponseDTO> buscarPorId(@Parameter(description = "ID do grupo") @PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Edita um grupo existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Grupo atualizado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'GRUPO_EDITAR' necessária)")
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ResponseEntity<GrupoResponseDTO> editarGrupo(@Parameter(description = "ID do grupo") @PathVariable String id, @RequestBody @Valid GrupoUpdateRequestDTO dto, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Exclui um grupo existente", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Grupo excluído com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'GRUPO_DELETAR' necessária)")
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ResponseEntity<Void> excluirGrupo(@Parameter(description = "ID do grupo") @PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Entra em uma Turma com código de convite", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Inscrição realizada com sucesso")
    @ApiResponse(responseCode = "400", description = "Código de convite inválido ou grupo não é uma Turma")
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ResponseEntity<Void> entrarComConvite(@Parameter(description = "ID do grupo (turma)") @PathVariable String id, @RequestBody @Valid ConviteRequestDTO dto, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Adiciona um membro a um Grupo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Membro adicionado com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'GRUPO_MEMBRO_ADICIONAR' necessária)")
    @ApiResponse(responseCode = "404", description = "Grupo ou usuário não encontrado")
    ResponseEntity<Void> adicionarMembro(@Parameter(description = "ID do grupo") @PathVariable String id, @RequestBody @Valid AdicionarMembroRequestDTO dto, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Remove um membro de um grupo (ou sai do grupo)", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Membro removido/saiu com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado")
    @ApiResponse(responseCode = "404", description = "Grupo ou membro não encontrado")
    ResponseEntity<Void> removerMembro(@Parameter(description = "ID do grupo") @PathVariable String id, @Parameter(description = "ID do membro a ser removido") @PathVariable String membroId, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Adiciona uma atividade a um grupo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Atividade adicionada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'GRUPO_ATIVIDADE_CRIAR' necessária)")
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ResponseEntity<Void> adicionarAtividade(@Parameter(description = "ID do grupo") @PathVariable String id, @RequestBody @Valid AdicionarAtividadeRequestDTO dto, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Lista os membros de um grupo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado (usuário não é membro do grupo)")
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ResponseEntity<List<MembroGrupoResponseDTO>> listarMembrosDoGrupo(@Parameter(description = "ID do grupo") @PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado);

    @Operation(summary = "Lista as atividades de um grupo", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso")
    @ApiResponse(responseCode = "403", description = "Acesso negado (usuário não é membro do grupo)")
    @ApiResponse(responseCode = "404", description = "Grupo não encontrado")
    ResponseEntity<List<AtividadeDoGrupoResponseDTO>> listarAtividades(@Parameter(description = "ID do grupo") @PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado);
}