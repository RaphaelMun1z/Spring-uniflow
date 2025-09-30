package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeEstudanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.profile.AtividadeAvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeEstudanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAvaliacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.services.AtividadeEstudanteService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades-estudante")
public class AtividadeEstudanteController extends GenericCrudControllerImpl<AtividadeEstudanteRequestDTO, AtividadeEstudanteResponseDTO> {
    private final AtividadeEstudanteService atividadeEstudanteService;

    protected AtividadeEstudanteController(AtividadeEstudanteService service) {
        super(service);
        this.atividadeEstudanteService = service;
    }

    @PostMapping("/{atividadeId}/entrega")
    public ResponseEntity<Void> entregarTarefa(@PathVariable String atividadeId) {
        atividadeEstudanteService.entregarTarefa(atividadeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/assinante/{assinanteId}/clonadas-do-grupo/{grupoId}")
    public ResponseEntity<List<AtividadeEstudanteResponseDTO>> getClonedActivities(
            @PathVariable String assinanteId,
            @PathVariable String grupoId,
            @RequestParam(required = false) StatusEntregaEnum status
    ) {
        List<AtividadeEstudanteResponseDTO> atividades = atividadeEstudanteService.findClonedActivitiesByGroup(assinanteId, grupoId, status);
        return ResponseEntity.ok(atividades);
    }

    @PostMapping("/{atividadeId}/avaliar")
    @PreAuthorize("isAuthenticated() and authentication.principal.hasRole('PROFESSOR') and @atividadeSecurityService.podeAvaliar(authentication, #atividadeId)")
    public ResponseEntity<AtividadeAvaliacaoResponseDTO> avaliarAtividade(
            @PathVariable("atividadeId") String atividadeId,
            @RequestBody @Valid AtividadeAvaliacaoRequestDTO avaliacaoDTO) {
        AtividadeAvaliacaoResponseDTO response = atividadeEstudanteService.avaliarAtividade(atividadeId, avaliacaoDTO);
        return ResponseEntity.ok(response);
    }
}
