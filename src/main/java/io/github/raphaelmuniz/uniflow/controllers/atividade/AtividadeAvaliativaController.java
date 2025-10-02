package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeEntregaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeEntregaService;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeAvaliativaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        AtividadeAvaliativaDetalhadaResponseDTO atividade = atividadeAvaliativaService.findById(id, usuarioLogado);
        return ResponseEntity.ok(atividade);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<AtividadeAvaliativaResponseDTO> updateAtividade(
            @PathVariable String id,
            @RequestBody @Valid AtividadeAvaliativaUpdateRequestDTO dto,
            @AuthenticationPrincipal Usuario professorLogado
    ) {
        AtividadeAvaliativaResponseDTO atividadeAtualizada = atividadeAvaliativaService.update(id, dto, professorLogado);
        return ResponseEntity.ok(atividadeAtualizada);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<Void> deleteAtividade(
            @PathVariable String id,
            @AuthenticationPrincipal Usuario professorLogado
    ) {
        atividadeAvaliativaService.delete(id, professorLogado);
        return ResponseEntity.noContent().build();
    }
}
