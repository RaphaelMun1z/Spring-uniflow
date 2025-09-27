package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.EstudanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.uniflow.services.EstudanteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController extends GenericCrudControllerImpl<EstudanteRequestDTO, EstudanteResponseDTO> {
    protected EstudanteController(EstudanteService service) {
        super(service);
    }
}
