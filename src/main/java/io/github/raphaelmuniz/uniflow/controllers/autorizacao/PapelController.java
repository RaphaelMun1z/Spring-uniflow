package io.github.raphaelmuniz.uniflow.controllers.autorizacao;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.services.autorizacao.PapelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/papeis")
public class PapelController extends GenericCrudControllerImpl<PapelRequestDTO, PapelResponseDTO> {
    protected PapelController(PapelService service) {
        super(service);
    }
}
