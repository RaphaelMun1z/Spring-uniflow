package io.github.raphaelmuniz.uniflow.services.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.repositories.grupo.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService extends GenericCrudServiceImpl<DisciplinaRequestDTO, DisciplinaResponseDTO, Disciplina, String> {
    protected DisciplinaService(DisciplinaRepository repository) {
        super(repository, DisciplinaRequestDTO::toModel, DisciplinaResponseDTO::new);
    }
}
