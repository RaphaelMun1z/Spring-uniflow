package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AtividadeCopiaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeCopiaResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AtividadeCopiaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atividades-copia")
public class AtividadeCopiaController extends GenericCrudControllerImpl<AtividadeCopiaRequestDTO, AtividadeCopiaResponseDTO> {
    protected AtividadeCopiaController(AtividadeCopiaService service) {
        super(service);
    }
}
