package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AtividadeModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AtividadeModeloService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atividades-modelo")
public class AtividadeModeloController extends GenericCrudControllerImpl<AtividadeModeloRequestDTO, AtividadeModeloResponseDTO> {
    protected AtividadeModeloController(AtividadeModeloService service) {
        super(service);
    }
}
