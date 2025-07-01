package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.TransmissaoDeNotificacaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.TransmissaoDeNotificacaoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.TransmissaoDeNotificacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transmissao-notificacao")
public class TransmissaoDeNotificacaoController extends GenericCrudControllerImpl<TransmissaoDeNotificacaoRequestDTO, TransmissaoDeNotificacaoResponseDTO> {
    protected TransmissaoDeNotificacaoController(TransmissaoDeNotificacaoService service) {
        super(service);
    }
}