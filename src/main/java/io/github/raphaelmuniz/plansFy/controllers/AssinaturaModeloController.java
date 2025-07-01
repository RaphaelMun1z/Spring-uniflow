package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AssinaturaModeloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assinaturas-modelo")
public class AssinaturaModeloController extends GenericCrudControllerImpl<AssinaturaModeloRequestDTO, AssinaturaModeloResponseDTO> {
    protected AssinaturaModeloController(AssinaturaModeloService service) {
        super(service);
    }
}
