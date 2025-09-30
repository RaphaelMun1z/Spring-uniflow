package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeEstudanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AtividadeEntregaService;
import io.github.raphaelmuniz.uniflow.services.AtividadeAvaliativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades-grupo")
public class AtividadeAvaliativaController extends GenericCrudControllerImpl<AtividadeAvaliativaRequestDTO, AtividadeAvaliativaResponseDTO> {
    @Autowired
    AtividadeEntregaService atividadeEntregaService;

    protected AtividadeAvaliativaController(AtividadeAvaliativaService service) {
        super(service);
    }

    @PostMapping("/{atividadeGrupoId}/clonar-para/{assinanteId}")
    public ResponseEntity<AtividadeEstudanteResponseDTO> clonarAtividadeParaAssinante(@PathVariable String atividadeGrupoId, @PathVariable String assinanteId) {
        AtividadeEstudanteResponseDTO copia = atividadeEntregaService.clonarAtividadeAvaliativa(assinanteId, atividadeGrupoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(copia);
    }

    @GetMapping("/{atividadeGrupoId}/copias")
    public ResponseEntity<List<AtividadeEstudanteResponseDTO>> atividadesClonadasDeAtividadeGrupo(@PathVariable String atividadeGrupoId) {
        List<AtividadeEstudanteResponseDTO> atividades = atividadeEntregaService.atividadesClonadasDeAtividadeAvaliativa(atividadeGrupoId);
        return ResponseEntity.ok(atividades);
    }
}
