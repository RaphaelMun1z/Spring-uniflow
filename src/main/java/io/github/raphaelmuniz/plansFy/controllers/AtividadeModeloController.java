package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AtividadeModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AtividadeModeloService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atividades-modelo")
public class AtividadeModeloController extends GenericCrudControllerImpl<AtividadeModeloRequestDTO, AtividadeModeloResponseDTO> {
    protected AtividadeModeloController(AtividadeModeloService service) {
        super(service);
    }
}
