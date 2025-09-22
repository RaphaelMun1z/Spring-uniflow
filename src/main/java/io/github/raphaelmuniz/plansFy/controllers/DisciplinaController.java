package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.DisciplinaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.DisciplinaResponseDTO;
import io.github.raphaelmuniz.plansFy.services.DisciplinaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController extends GenericCrudControllerImpl<DisciplinaRequestDTO, DisciplinaResponseDTO> {
    protected DisciplinaController(DisciplinaService service) {
        super(service);
    }
}
