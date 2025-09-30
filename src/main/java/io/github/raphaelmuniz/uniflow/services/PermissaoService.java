package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.PermissaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PermissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Permissao;
import io.github.raphaelmuniz.uniflow.repositories.PermissaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService extends GenericCrudServiceImpl<PermissaoRequestDTO, PermissaoResponseDTO, Permissao, String> {
    protected PermissaoService(PermissaoRepository repository) {
        super(repository, PermissaoRequestDTO::toModel, PermissaoResponseDTO::new);
    }
}
