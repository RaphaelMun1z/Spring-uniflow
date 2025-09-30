package io.github.raphaelmuniz.uniflow.controllers.notificacao;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.services.notificacao.NotificacaoAssinanteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notificacoes-assinante")
public class NotificacaoAssinanteController extends GenericCrudControllerImpl<NotificacaoAssinanteRequestDTO, NotificacaoAssinanteResponseDTO> {
    protected NotificacaoAssinanteController(NotificacaoAssinanteService service) {
        super(service);
    }
}
