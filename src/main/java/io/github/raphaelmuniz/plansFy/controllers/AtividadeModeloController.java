package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.AtividadeModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AtividadeModeloService;
import io.github.raphaelmuniz.plansFy.services.interfaces.CrudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades-modelo")
public class AtividadeModeloController extends GenericCrudControllerImpl<AtividadeModeloRequestDTO, AtividadeModeloResponseDTO> {
    protected AtividadeModeloController(AtividadeModeloService service) {
        super(service);
    }
}
