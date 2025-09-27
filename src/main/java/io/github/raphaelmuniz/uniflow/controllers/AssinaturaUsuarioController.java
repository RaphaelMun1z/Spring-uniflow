package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AssinaturaUsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assinaturas-usuario")
public class AssinaturaUsuarioController extends GenericCrudControllerImpl<AssinaturaUsuarioRequestDTO, AssinaturaUsuarioResponseDTO> {
    protected AssinaturaUsuarioController(AssinaturaUsuarioService service) {
        super(service);
    }
}
