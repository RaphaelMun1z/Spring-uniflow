package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.InscricaoGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.InscricaoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.grupo.InscricaoGrupoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscricao-grupo")
public class InscricaoGrupoController extends GenericCrudControllerImpl<InscricaoGrupoRequestDTO, InscricaoGrupoResponseDTO> {
    protected InscricaoGrupoController(InscricaoGrupoService service) {
        super(service);
    }
}
