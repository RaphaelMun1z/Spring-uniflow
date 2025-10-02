package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeEntregaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeEntregaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AvaliacaoAtividadeResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeEntregaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atividades-entrega")
public class AtividadeEntregaController extends GenericCrudControllerImpl<AtividadeEntregaRequestDTO, AtividadeEntregaResponseDTO> {
    private final AtividadeEntregaService atividadeEntregaService;

    protected AtividadeEntregaController(AtividadeEntregaService service) {
        super(service);
        this.atividadeEntregaService = service;
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ESTUDANTE')")
    public ResponseEntity<Void> entregarAtividade(
            @PathVariable String id,
            @RequestBody @Valid AtividadeEntregaRequestDTO dto,
            @AuthenticationPrincipal Estudante estudanteLogado) {

        atividadeEntregaService.entregarAtividade(id, dto, estudanteLogado);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/avaliar")
    @PreAuthorize("hasRole('PROFESSOR')")
    public ResponseEntity<AvaliacaoAtividadeResponseDTO> avaliarEntrega(
            @PathVariable String id,
            @RequestBody @Valid AvaliacaoRequestDTO dto,
            @AuthenticationPrincipal Professor professorLogado) {

        AvaliacaoAtividadeResponseDTO avaliacao = atividadeEntregaService.avaliarEntrega(id, dto, professorLogado);

        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
    }
}
