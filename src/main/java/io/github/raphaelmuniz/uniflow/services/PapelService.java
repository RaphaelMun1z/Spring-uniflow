package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Papel;
import io.github.raphaelmuniz.uniflow.repositories.PapelRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class PapelService extends GenericCrudServiceImpl<PapelRequestDTO, PapelResponseDTO, Papel, String> {
    protected PapelService(PapelRepository repository) {
        super(repository, PapelRequestDTO::toModel, PapelResponseDTO::new);
    }
}
