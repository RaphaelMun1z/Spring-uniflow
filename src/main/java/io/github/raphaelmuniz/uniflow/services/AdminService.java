package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AdminRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AdminResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Admin;
import io.github.raphaelmuniz.uniflow.repositories.AdminRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends GenericCrudServiceImpl<AdminRequestDTO, AdminResponseDTO, Admin, String> {
    protected AdminService(AdminRepository repository) {
        super(repository, AdminRequestDTO::toModel, AdminResponseDTO::new);
    }
}
