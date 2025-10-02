package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.services.grupo.DisciplinaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController extends GenericCrudControllerImpl<DisciplinaRequestDTO, DisciplinaResponseDTO> {
    protected DisciplinaController(DisciplinaService service) {
        super(service);
    }
}
