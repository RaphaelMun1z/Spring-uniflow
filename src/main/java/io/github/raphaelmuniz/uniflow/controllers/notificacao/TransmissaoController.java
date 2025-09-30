package io.github.raphaelmuniz.uniflow.controllers.notificacao;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.notificacao.TransmissaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.TransmissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.notificacao.TransmissaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transmissao-notificacao")
public class TransmissaoController extends GenericCrudControllerImpl<TransmissaoRequestDTO, TransmissaoResponseDTO> {
    protected TransmissaoController(TransmissaoService service) {
        super(service);
    }
}