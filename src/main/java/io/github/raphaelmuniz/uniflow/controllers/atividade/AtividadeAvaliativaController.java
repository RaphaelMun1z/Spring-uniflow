package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeAvaliativaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atividades-avaliativas")
public class AtividadeAvaliativaController {
    private final AtividadeAvaliativaService atividadeAvaliativaService;

    public AtividadeAvaliativaController(AtividadeAvaliativaService atividadeAvaliativaService) {
        this.atividadeAvaliativaService = atividadeAvaliativaService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AtividadeAvaliativaDetalhadaResponseDTO> getAtividadeById(
            @PathVariable String id,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        AtividadeAvaliativaDetalhadaResponseDTO atividade = atividadeAvaliativaService.buscarPorId(id, usuarioLogado);
        return ResponseEntity.ok(atividade);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<AtividadeAvaliativaResponseDTO> updateAtividade(
            @PathVariable String id,
            @RequestBody @Valid AtividadeAvaliativaUpdateRequestDTO dto,
            @AuthenticationPrincipal Usuario professorLogado
    ) {
        AtividadeAvaliativaResponseDTO atividadeAtualizada = atividadeAvaliativaService.atualizar(id, dto, professorLogado);
        return ResponseEntity.ok(atividadeAtualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> deleteAtividade(
            @PathVariable String id,
            @AuthenticationPrincipal Usuario professorLogado
    ) {
        atividadeAvaliativaService.deletar(id, professorLogado);
        return ResponseEntity.noContent().build();
    }
}
