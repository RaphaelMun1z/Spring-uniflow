package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.repositories.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AssinaturaUsuarioService extends GenericCrudServiceImpl<AssinaturaUsuarioRequestDTO, AssinaturaUsuarioResponseDTO, AssinaturaUsuario, String> {
    protected AssinaturaUsuarioService(AssinaturaUsuarioRepository repository) {
        super(repository, AssinaturaUsuarioRequestDTO::toModel, AssinaturaUsuarioResponseDTO::new);
    }
}
