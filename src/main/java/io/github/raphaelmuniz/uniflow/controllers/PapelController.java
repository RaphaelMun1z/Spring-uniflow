package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.services.PapelService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/papeis")
public class PapelController extends GenericCrudControllerImpl<PapelRequestDTO, PapelResponseDTO> {
    protected PapelController(PapelService service) {
        super(service);
    }
}
