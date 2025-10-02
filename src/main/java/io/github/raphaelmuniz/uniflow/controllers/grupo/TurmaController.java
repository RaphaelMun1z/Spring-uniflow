package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeAvaliativaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/turmas")
public class TurmaController {

    private final AtividadeAvaliativaService atividadeAvaliativaService;

    public TurmaController(AtividadeAvaliativaService atividadeAvaliativaService) {
        this.atividadeAvaliativaService = atividadeAvaliativaService;
    }

    @PostMapping("/{id}/atividades-avaliativas")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<AtividadeAvaliativaResponseDTO> criarAtividadeAvaliativa(
            @PathVariable("id") String turmaId,
            @RequestBody @Valid AtividadeAvaliativaRequestDTO dto,
            @AuthenticationPrincipal Usuario professorLogado) {

        AtividadeAvaliativaResponseDTO novaAtividade = atividadeAvaliativaService.criarEAssociarAtividade(turmaId, dto, professorLogado);

        URI location = URI.create(String.format("/api/atividades-avaliativas/%s", novaAtividade.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAtividade);
    }
}