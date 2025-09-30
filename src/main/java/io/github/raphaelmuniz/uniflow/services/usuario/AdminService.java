package io.github.raphaelmuniz.uniflow.services.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.AdminRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AdminResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Admin;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AdminRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdminService extends GenericCrudServiceImpl<AdminRequestDTO, AdminResponseDTO, Admin, String> {
    protected AdminService(AdminRepository repository) {
        super(repository, AdminRequestDTO::toModel, AdminResponseDTO::new);
    }
}
