package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.controllers.atividade.docs.AtividadeColaborativaControllerDocs;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeColaborativaUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.TarefaStatusUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeColaborativaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeColaborativaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atividades-colaborativas")
public class AtividadeColaborativaController implements AtividadeColaborativaControllerDocs {

    private final AtividadeColaborativaService service;

    public AtividadeColaborativaController(AtividadeColaborativaService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<AtividadeColaborativaDetalhadaResponseDTO> buscarPorId(
        @PathVariable String id,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(service.buscarPorId(id, usuarioLogado));
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ATIVIDADE_COLABORATIVA_EDITAR')")
    @Override
    public ResponseEntity<AtividadeColaborativaDetalhadaResponseDTO> atualizar(
        @PathVariable String id,
        @RequestBody @Valid AtividadeColaborativaUpdateRequestDTO dto,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        return ResponseEntity.ok(service.atualizar(id, dto, usuarioLogado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ATIVIDADE_COLABORATIVA_DELETAR')")
    @Override
    public ResponseEntity<Void> deletar(
        @PathVariable String id,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        service.deletar(id, usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/meu-status")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<Void> atualizarMeuStatusNaAtividade(
        @PathVariable("id") String atividadeId,
        @RequestBody @Valid TarefaStatusUpdateRequestDTO dto,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        service.atualizarMeuStatus(atividadeId, dto, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}