package io.github.raphaelmuniz.uniflow.controllers.assinatura;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaUsuarioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assinaturas-usuario")
public class AssinaturaUsuarioController extends GenericCrudControllerImpl<AssinaturaUsuarioRequestDTO, AssinaturaUsuarioResponseDTO> {
    protected AssinaturaUsuarioController(AssinaturaUsuarioService service) {
        super(service);
    }
}
