package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.TarefaStatusUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeColaborativaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atividades-colaborativas")
public class AtividadeColaborativaController {

    private final AtividadeColaborativaService service;

    public AtividadeColaborativaController(AtividadeColaborativaService service) {
        this.service = service;
    }

    @PatchMapping("/{id}/meu-status")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> atualizarMeuStatusNaAtividade(
            @PathVariable("id") String atividadeId,
            @RequestBody @Valid TarefaStatusUpdateRequestDTO dto,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        service.atualizarMeuStatus(atividadeId, dto, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}