package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.services.DisciplinaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController extends GenericCrudControllerImpl<DisciplinaRequestDTO, DisciplinaResponseDTO> {
    protected DisciplinaController(DisciplinaService service) {
        super(service);
    }
}
