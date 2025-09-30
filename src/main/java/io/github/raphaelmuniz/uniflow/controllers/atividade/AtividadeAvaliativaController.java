package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeEntregaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeEntregaService;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeAvaliativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atividades-avaliativas")
public class AtividadeAvaliativaController extends GenericCrudControllerImpl<AtividadeAvaliativaRequestDTO, AtividadeAvaliativaResponseDTO> {
    @Autowired
    AtividadeEntregaService atividadeEntregaService;

    protected AtividadeAvaliativaController(AtividadeAvaliativaService service) {
        super(service);
    }

    @PostMapping("/{atividadeGrupoId}/clonar-para/{assinanteId}")
    public ResponseEntity<AtividadeEntregaResponseDTO> clonarAtividadeParaAssinante(@PathVariable String atividadeGrupoId, @PathVariable String assinanteId) {
        AtividadeEntregaResponseDTO copia = atividadeEntregaService.clonarAtividadeAvaliativa(assinanteId, atividadeGrupoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(copia);
    }

    @GetMapping("/{atividadeGrupoId}/copias")
    public ResponseEntity<List<AtividadeEntregaResponseDTO>> atividadesClonadasDeAtividadeGrupo(@PathVariable String atividadeGrupoId) {
        List<AtividadeEntregaResponseDTO> atividades = atividadeEntregaService.atividadesClonadasDeAtividadeAvaliativa(atividadeGrupoId);
        return ResponseEntity.ok(atividades);
    }
}
