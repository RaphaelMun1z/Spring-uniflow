package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AtividadeGrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atividades-copia")
public class AtividadeCopiaController extends GenericCrudControllerImpl<AtividadeGrupoRequestDTO, AtividadeGrupoResponseDTO> {
    protected AtividadeCopiaController(AtividadeGrupoService service) {
        super(service);
    }
}
