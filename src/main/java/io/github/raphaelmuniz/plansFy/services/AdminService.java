package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AdminRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AdminResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Admin;
import io.github.raphaelmuniz.plansFy.repositories.AdminRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends GenericCrudServiceImpl<AdminRequestDTO, AdminResponseDTO, Admin, String> {
    protected AdminService(AdminRepository repository) {
        super(repository, AdminRequestDTO::toModel, AdminResponseDTO::new);
    }
}
