package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.profile.AtividadeAvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAvaliacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.services.AtividadeAssinanteService;
import io.github.raphaelmuniz.uniflow.services.AtividadeGrupoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades-assinante")
public class AtividadeAssinanteController extends GenericCrudControllerImpl<AtividadeAssinanteRequestDTO, AtividadeAssinanteResponseDTO> {
    private final AtividadeAssinanteService atividadeAssinanteService;

    protected AtividadeAssinanteController(AtividadeAssinanteService service) {
        super(service);
        this.atividadeAssinanteService = service;
    }

    @PostMapping("/{atividadeId}/entrega")
    public ResponseEntity<Void> entregarTarefa(@PathVariable String atividadeId) {
        atividadeAssinanteService.entregarTarefa(atividadeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/assinante/{assinanteId}/clonadas-do-grupo/{grupoId}")
    public ResponseEntity<List<AtividadeAssinanteResponseDTO>> getClonedActivities(
            @PathVariable String assinanteId,
            @PathVariable String grupoId,
            @RequestParam(required = false) StatusEntregaEnum status
    ) {
        List<AtividadeAssinanteResponseDTO> atividades = atividadeAssinanteService.findClonedActivitiesByGroup(assinanteId, grupoId, status);
        return ResponseEntity.ok(atividades);
    }

    @PostMapping("/{atividadeId}/avaliar")
    @PreAuthorize("isAuthenticated() and authentication.principal.hasRole('PROFESSOR') and @atividadeSecurityService.podeAvaliar(authentication, #atividadeId)")
    public ResponseEntity<AtividadeAvaliacaoResponseDTO> avaliarAtividade(
            @PathVariable("atividadeId") String atividadeId,
            @RequestBody @Valid AtividadeAvaliacaoRequestDTO avaliacaoDTO) {
        AtividadeAvaliacaoResponseDTO response = atividadeAssinanteService.avaliarAtividade(atividadeId, avaliacaoDTO);
        return ResponseEntity.ok(response);
    }
}
