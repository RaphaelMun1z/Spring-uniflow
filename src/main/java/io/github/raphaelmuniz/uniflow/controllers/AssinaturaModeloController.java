package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AssinaturaModeloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assinaturas-modelo")
public class AssinaturaModeloController extends GenericCrudControllerImpl<AssinaturaModeloRequestDTO, AssinaturaModeloResponseDTO> {
    protected AssinaturaModeloController(AssinaturaModeloService service) {
        super(service);
    }
}
