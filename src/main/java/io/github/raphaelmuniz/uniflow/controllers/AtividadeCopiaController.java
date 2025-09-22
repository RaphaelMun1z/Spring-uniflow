package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeCopiaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeCopiaResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AtividadeCopiaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atividades-copia")
public class AtividadeCopiaController extends GenericCrudControllerImpl<AtividadeCopiaRequestDTO, AtividadeCopiaResponseDTO> {
    protected AtividadeCopiaController(AtividadeCopiaService service) {
        super(service);
    }
}
