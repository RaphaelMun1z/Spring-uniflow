package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.PagamentoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController extends GenericCrudControllerImpl<PagamentoRequestDTO, PagamentoResponseDTO> {
    protected PagamentoController(PagamentoService service) {
        super(service);
    }
}
