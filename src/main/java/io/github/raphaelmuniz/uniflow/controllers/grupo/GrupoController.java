package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.*;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.AtividadeDoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.MembroGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.grupo.GrupoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/grupos")
public class GrupoController extends GenericCrudControllerImpl<GrupoRequestDTO, GrupoResponseDTO> {
    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        super(grupoService);
        this.grupoService = grupoService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GrupoResponseDTO> criarGrupo(
            @RequestBody @Valid GrupoRequestDTO grupoRequestDTO,
            @AuthenticationPrincipal Usuario usuarioAutenticado
    ) {
        GrupoResponseDTO novoGrupo = grupoService.criarGrupo(grupoRequestDTO, usuarioAutenticado);

        URI location = URI.create("/grupos/" + novoGrupo.getId());
        return ResponseEntity.created(location).body(novoGrupo);
    }

    @PostMapping("/{id}/convite")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> entrarComConvite(
            @PathVariable String id,
            @RequestBody @Valid ConviteRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        grupoService.juntarComConvite(id, dto.codigoConvite(), usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/membros")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> adicionarMembro(
            @PathVariable String id,
            @RequestBody @Valid AdicionarMembroRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        grupoService.adicionarMembroDiretamente(id, dto.usuarioId(), usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/membros/{membroId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> alterarPapel(
            @PathVariable String id,
            @PathVariable String membroId,
            @RequestBody @Valid AlterarPapelRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        grupoService.alterarPapelMembro(id, membroId, dto.novoPapel(), usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/membros/{membroId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> removerMembro(
            @PathVariable String id,
            @PathVariable String membroId,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        grupoService.removerMembro(id, membroId, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<GrupoResponseDTO> editarGrupo(
            @PathVariable String id,
            @RequestBody @Valid GrupoUpdateRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        GrupoResponseDTO grupoAtualizado = grupoService.editarGrupo(id, dto, usuarioLogado);
        return ResponseEntity.ok(grupoAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> excluirGrupo(
            @PathVariable String id,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        grupoService.excluirGrupo(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/subgrupos")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<GrupoResponseDTO> criarSubGrupo(
            @PathVariable String id,
            @RequestBody @Valid SubGrupoRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        GrupoResponseDTO novoSubGrupo = grupoService.criarSubGrupo(id, dto, usuarioLogado);
        URI location = URI.create("/grupos/" + novoSubGrupo.getId());
        return ResponseEntity.created(location).body(novoSubGrupo);
    }

    @PostMapping("/{id}/atividades")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> adicionarAtividade(
            @PathVariable String id,
            @RequestBody @Valid AdicionarAtividadeRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        grupoService.adicionarAtividade(id, dto, usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/membros")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<MembroGrupoResponseDTO>> listarMembrosDoGrupo(
            @PathVariable String id, @
                    AuthenticationPrincipal Usuario usuarioLogado) {
        List<MembroGrupoResponseDTO> membros = grupoService.listarMembrosDoGrupo(id, usuarioLogado);
        return ResponseEntity.ok(membros);
    }

    @GetMapping("/{id}/atividades")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AtividadeDoGrupoResponseDTO>> listarAtividades(@PathVariable String id, @AuthenticationPrincipal Usuario usuarioLogado) {
        List<AtividadeDoGrupoResponseDTO> atividades = grupoService.listarAtividadesDoGrupo(id, usuarioLogado);
        return ResponseEntity.ok(atividades);
    }
}
