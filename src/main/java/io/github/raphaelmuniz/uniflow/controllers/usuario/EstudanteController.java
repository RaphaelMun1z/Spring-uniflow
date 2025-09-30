package io.github.raphaelmuniz.uniflow.controllers.usuario;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.usuario.EstudanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.EstudanteResponseDTO;
import io.github.raphaelmuniz.uniflow.services.usuario.EstudanteService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estudantes")
public class EstudanteController extends GenericCrudControllerImpl<EstudanteRequestDTO, EstudanteResponseDTO> {
    protected EstudanteController(EstudanteService service) {
        super(service);
    }
}
