package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.services.AtividadeAssinanteService;
import io.github.raphaelmuniz.uniflow.services.AtividadeGrupoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades-assinante")
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
}
