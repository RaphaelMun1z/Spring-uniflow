package io.github.raphaelmuniz.uniflow.controllers.assinatura;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaModeloService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assinaturas-modelo")
public class AssinaturaModeloController extends GenericCrudControllerImpl<AssinaturaModeloRequestDTO, AssinaturaModeloResponseDTO> {
    protected AssinaturaModeloController(AssinaturaModeloService service) {
        super(service);
    }
}
