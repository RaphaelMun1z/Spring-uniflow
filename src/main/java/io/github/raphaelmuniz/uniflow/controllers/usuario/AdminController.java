package io.github.raphaelmuniz.uniflow.controllers.usuario;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.usuario.AdminRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AdminResponseDTO;
import io.github.raphaelmuniz.uniflow.services.usuario.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    protected AdminController(AdminService service) {

    }
}
