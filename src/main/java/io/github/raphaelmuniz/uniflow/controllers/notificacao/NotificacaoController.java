package io.github.raphaelmuniz.uniflow.controllers.notificacao;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.notificacao.NotificacaoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController extends GenericCrudControllerImpl<NotificacaoRequestDTO, NotificacaoResponseDTO> {
    protected NotificacaoController(NotificacaoService service) {
        super(service);
    }
}
