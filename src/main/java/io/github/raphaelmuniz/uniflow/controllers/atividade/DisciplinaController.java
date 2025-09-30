package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.services.atividade.DisciplinaService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController extends GenericCrudControllerImpl<DisciplinaRequestDTO, DisciplinaResponseDTO> {
    protected DisciplinaController(DisciplinaService service) {
        super(service);
    }
}
