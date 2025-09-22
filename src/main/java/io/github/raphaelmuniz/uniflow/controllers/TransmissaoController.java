package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.TransmissaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.TransmissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.TransmissaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transmissao-notificacao")
public class TransmissaoController extends GenericCrudControllerImpl<TransmissaoRequestDTO, TransmissaoResponseDTO> {
    protected TransmissaoController(TransmissaoService service) {
        super(service);
    }
}