package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AtividadeCopiaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeCopiaResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.repositories.AtividadeCopiaRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AtividadeCopiaService extends GenericCrudServiceImpl<AtividadeCopiaRequestDTO, AtividadeCopiaResponseDTO, AtividadeCopia, String> {
    protected AtividadeCopiaService(AtividadeCopiaRepository repository) {
        super(repository, AtividadeCopiaRequestDTO::toModel, AtividadeCopiaResponseDTO::new);
    }
}
