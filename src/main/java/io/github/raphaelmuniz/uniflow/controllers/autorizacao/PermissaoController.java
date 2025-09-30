package io.github.raphaelmuniz.uniflow.controllers.autorizacao;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PermissaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PermissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.autorizacao.PermissaoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/permissoes")
public class PermissaoController extends GenericCrudControllerImpl<PermissaoRequestDTO, PermissaoResponseDTO> {
    protected PermissaoController(PermissaoService service) {
        super(service);
    }
}
