package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.NotificacaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.NotificacaoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.NotificacaoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notificacoes")
public class NotificacaoController extends GenericCrudControllerImpl<NotificacaoRequestDTO, NotificacaoResponseDTO> {
    protected NotificacaoController(NotificacaoService service) {
        super(service);
    }
}
