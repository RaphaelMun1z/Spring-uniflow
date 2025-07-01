package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AdminRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AdminResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AdminService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController extends GenericCrudControllerImpl<AdminRequestDTO, AdminResponseDTO> {
    protected AdminController(AdminService service) {
        super(service);
    }
}
