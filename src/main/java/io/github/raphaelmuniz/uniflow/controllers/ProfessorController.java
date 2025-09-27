package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.ProfessorRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.uniflow.services.ProfessorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController extends GenericCrudControllerImpl<ProfessorRequestDTO, ProfessorResponseDTO> {
    protected ProfessorController(ProfessorService service) {
        super(service);
    }
}
