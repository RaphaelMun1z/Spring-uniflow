package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AssinaturaUsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assinaturas-usuario")
public class AssinaturaUsuarioController extends GenericCrudControllerImpl<AssinaturaUsuarioRequestDTO, AssinaturaUsuarioResponseDTO> {
    protected AssinaturaUsuarioController(AssinaturaUsuarioService service) {
        super(service);
    }
}
