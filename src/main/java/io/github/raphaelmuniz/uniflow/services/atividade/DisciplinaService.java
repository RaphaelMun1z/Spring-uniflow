package io.github.raphaelmuniz.uniflow.services.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.Disciplina;
import io.github.raphaelmuniz.uniflow.repositories.atividade.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class DisciplinaService extends GenericCrudServiceImpl<DisciplinaRequestDTO, DisciplinaResponseDTO, Disciplina, String> {
    protected DisciplinaService(DisciplinaRepository repository) {
        super(repository, DisciplinaRequestDTO::toModel, DisciplinaResponseDTO::new);
    }
}
