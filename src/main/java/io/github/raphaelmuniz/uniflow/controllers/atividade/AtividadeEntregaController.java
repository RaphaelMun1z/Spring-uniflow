package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeEntregaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeEntregaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AvaliacaoAtividadeResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeEntregaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades-entrega")
public class AtividadeEntregaController extends GenericCrudControllerImpl<AtividadeEntregaRequestDTO, AtividadeEntregaResponseDTO> {
    private final AtividadeEntregaService atividadeEntregaService;

    protected AtividadeEntregaController(AtividadeEntregaService service) {
        super(service);
        this.atividadeEntregaService = service;
    }

    @PostMapping("/{atividadeId}/entrega")
    public ResponseEntity<Void> entregarTarefa(@PathVariable String atividadeId) {
        atividadeEntregaService.entregarTarefa(atividadeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/assinante/{assinanteId}/clonadas-do-grupo/{grupoId}")
    public ResponseEntity<List<AtividadeEntregaResponseDTO>> getClonedActivities(
            @PathVariable String assinanteId,
            @PathVariable String grupoId,
            @RequestParam(required = false) StatusEntregaEnum status
    ) {
        List<AtividadeEntregaResponseDTO> atividades = atividadeEntregaService.findClonedActivitiesByGroup(assinanteId, grupoId, status);
        return ResponseEntity.ok(atividades);
    }

    @PostMapping("/{atividadeId}/avaliar")
    @PreAuthorize("isAuthenticated() and authentication.principal.hasRole('PROFESSOR') and @atividadeSecurityService.podeAvaliar(authentication, #atividadeId)")
    public ResponseEntity<AvaliacaoAtividadeResponseDTO> avaliarAtividade(
            @PathVariable("atividadeId") String atividadeId,
            @RequestBody @Valid AtividadeAvaliacaoRequestDTO avaliacaoDTO) {
        AvaliacaoAtividadeResponseDTO response = atividadeEntregaService.avaliarAtividade(atividadeId, avaliacaoDTO);
        return ResponseEntity.ok(response);
    }
}
