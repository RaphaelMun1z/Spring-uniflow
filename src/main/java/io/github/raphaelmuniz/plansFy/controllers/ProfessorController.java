package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.ProfessorRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.plansFy.services.ProfessorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professores")
public class ProfessorController extends GenericCrudControllerImpl<ProfessorRequestDTO, ProfessorResponseDTO> {
    protected ProfessorController(ProfessorService service) {
        super(service);
    }
}
