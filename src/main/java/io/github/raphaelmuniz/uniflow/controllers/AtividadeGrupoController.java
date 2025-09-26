package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AtividadeAssinanteService;
import io.github.raphaelmuniz.uniflow.services.AtividadeGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atividades-grupo")
public class AtividadeGrupoController extends GenericCrudControllerImpl<AtividadeGrupoRequestDTO, AtividadeGrupoResponseDTO> {
    @Autowired
    AtividadeAssinanteService atividadeAssinanteService;

    protected AtividadeGrupoController(AtividadeGrupoService service) {
        super(service);
    }

    @PostMapping("/{atividadeGrupoId}/clonar-para/{assinanteId}")
    public ResponseEntity<AtividadeAssinanteResponseDTO> clonarAtividadeParaAssinante(@PathVariable String atividadeGrupoId, @PathVariable String assinanteId) {
        AtividadeAssinanteResponseDTO copia = atividadeAssinanteService.clonarAtividadeGrupo(assinanteId, atividadeGrupoId);
        return ResponseEntity.status(HttpStatus.CREATED).body(copia);
    }
}
