package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AdminRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AdminResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController extends GenericCrudControllerImpl<AdminRequestDTO, AdminResponseDTO> {
    protected AdminController(AdminService service) {
        super(service);
    }
}
