package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeEstudanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AtividadeEstudanteService;
import io.github.raphaelmuniz.uniflow.services.AtividadeGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades-grupo")
public class AtividadeGrupoController extends GenericCrudControllerImpl<AtividadeGrupoRequestDTO, AtividadeGrupoResponseDTO> {
    @Autowired
    AtividadeEstudanteService atividadeEstudanteService;

    protected AtividadeGrupoController(AtividadeGrupoService service) {
        super(service);
    }

    @PostMapping("/{atividadeGrupoId}/clonar-para/{assinanteId}")
    public ResponseEntity<AtividadeEstudanteResponseDTO> clonarAtividadeParaAssinante(@PathVariable String atividadeGrupoId, @PathVariable String assinanteId) {
        AtividadeEstudanteResponseDTO copia = atividadeEstudanteService.clonarAtividadeGrupo(assinanteId, atividadeGrupoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(copia);
    }

    @GetMapping("/{atividadeGrupoId}/copias")
    public ResponseEntity<List<AtividadeEstudanteResponseDTO>> atividadesClonadasDeAtividadeGrupo(@PathVariable String atividadeGrupoId) {
        List<AtividadeEstudanteResponseDTO> atividades = atividadeEstudanteService.atividadesClonadasDeAtividadeGrupo(atividadeGrupoId);
        return ResponseEntity.ok(atividades);
    }
}
