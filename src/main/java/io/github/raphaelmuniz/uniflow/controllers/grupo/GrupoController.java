package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.controllers.grupo.docs.GrupoControllerDocs;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.*;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.AtividadeDoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.MembroGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.grupo.GrupoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController implements GrupoControllerDocs {
    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<GrupoResponseDTO> criarGrupo(
        @RequestBody @Valid GrupoRequestDTO grupoRequestDTO,
        @AuthenticationPrincipal Usuario usuarioAutenticado) {
        GrupoResponseDTO novoGrupo = grupoService.criarGrupo(grupoRequestDTO, usuarioAutenticado);
        URI location = URI.create("/api/grupos/" + novoGrupo.id());
        return ResponseEntity.created(location).body(novoGrupo);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<PaginatedResponse<GrupoResponseDTO>> buscarTodos(Pageable pageable) {
        Page<GrupoResponseDTO> page = grupoService.buscarTodos(pageable);
        PaginatedResponse<GrupoResponseDTO> response = new PaginatedResponse<>(
            page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<GrupoResponseDTO> buscarPorId(@PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(grupoService.buscarPorId(id, usuarioLogado));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('GRUPO_EDITAR')")
    @Override
    public ResponseEntity<GrupoResponseDTO> editarGrupo(
        @PathVariable String id,
        @RequestBody @Valid GrupoUpdateRequestDTO dto,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        GrupoResponseDTO grupoAtualizado = grupoService.editarGrupo(id, dto, usuarioLogado);
        return ResponseEntity.ok(grupoAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('GRUPO_DELETAR')")
    @Override
    public ResponseEntity<Void> excluirGrupo(
        @PathVariable String id,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        grupoService.excluirGrupo(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/convite")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<Void> entrarComConvite(
        @PathVariable String id,
        @RequestBody @Valid ConviteRequestDTO dto,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        grupoService.juntarComConvite(id, dto.codigoConvite(), usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/membros")
    @PreAuthorize("hasAuthority('GRUPO_MEMBRO_ADICIONAR')")
    @Override
    public ResponseEntity<Void> adicionarMembro(
        @PathVariable String id,
        @RequestBody @Valid AdicionarMembroRequestDTO dto,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        grupoService.adicionarMembroDiretamente(id, dto.usuarioId(), usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/membros/{membroId}")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<Void> removerMembro(
        @PathVariable String id,
        @PathVariable String membroId,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        grupoService.removerMembro(id, membroId, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/atividades")
    @PreAuthorize("hasAuthority('GRUPO_ATIVIDADE_CRIAR')")
    @Override
    public ResponseEntity<Void> adicionarAtividade(
        @PathVariable String id,
        @RequestBody @Valid AdicionarAtividadeRequestDTO dto,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        grupoService.adicionarAtividade(id, dto, usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/membros")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<List<MembroGrupoResponseDTO>> listarMembrosDoGrupo(
        @PathVariable String id,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        List<MembroGrupoResponseDTO> membros = grupoService.listarMembrosDoGrupo(id, usuarioLogado);
        return ResponseEntity.ok(membros);
    }

    @GetMapping("/{id}/atividades")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<List<AtividadeDoGrupoResponseDTO>> listarAtividades(
        @PathVariable String id,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        List<AtividadeDoGrupoResponseDTO> atividades = grupoService.listarAtividadesDoGrupo(id, usuarioLogado);
        return ResponseEntity.ok(atividades);
    }
}