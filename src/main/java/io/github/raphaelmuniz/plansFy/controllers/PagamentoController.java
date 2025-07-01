package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.PagamentoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController extends GenericCrudControllerImpl<PagamentoRequestDTO, PagamentoResponseDTO> {
    protected PagamentoController(PagamentoService service) {
        super(service);
    }
}
