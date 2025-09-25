package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AtividadeAssinanteService;
import io.github.raphaelmuniz.uniflow.services.AtividadeGrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atividades-grupo")
public class AtividadeGrupoController extends GenericCrudControllerImpl<AtividadeGrupoRequestDTO, AtividadeGrupoResponseDTO> {
    protected AtividadeGrupoController(AtividadeGrupoService service) {
        super(service);
    }
}
