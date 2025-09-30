package io.github.raphaelmuniz.uniflow.controllers.usuario;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.usuario.ProfessorRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.ProfessorResponseDTO;
import io.github.raphaelmuniz.uniflow.services.usuario.ProfessorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/professores")
public class ProfessorController extends GenericCrudControllerImpl<ProfessorRequestDTO, ProfessorResponseDTO> {
    protected ProfessorController(ProfessorService service) {
        super(service);
    }
}
