package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.TransmissaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.TransmissaoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.TransmissaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transmissao-notificacao")
public class TransmissaoController extends GenericCrudControllerImpl<TransmissaoRequestDTO, TransmissaoResponseDTO> {
    protected TransmissaoController(TransmissaoService service) {
        super(service);
    }
}