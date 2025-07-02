package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AssinanteRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AssinanteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assinantes")
public class AssinanteController extends GenericCrudControllerImpl<AssinanteRequestDTO, AssinanteResponseDTO> {
    protected AssinanteController(AssinanteService service) {
        super(service);
    }
}
