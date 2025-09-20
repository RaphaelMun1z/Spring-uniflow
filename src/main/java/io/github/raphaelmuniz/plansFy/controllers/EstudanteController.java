package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.plansFy.services.EstudanteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController extends GenericCrudControllerImpl<EstudanteRequestDTO, EstudanteResponseDTO> {
    protected EstudanteController(EstudanteService service) {
        super(service);
    }
}
