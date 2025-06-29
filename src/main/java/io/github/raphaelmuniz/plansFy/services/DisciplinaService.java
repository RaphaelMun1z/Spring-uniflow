package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.DisciplinaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.DisciplinaResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Disciplina;
import io.github.raphaelmuniz.plansFy.repositories.DisciplinaRepository;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DisciplinaService extends GenericCrudServiceImpl<DisciplinaRequestDTO, DisciplinaResponseDTO, Disciplina, String> {
    //@Autowired
    //DisciplinaRepository repository;

    //@Autowired
    //AtividadeModeloService atividadeService;

    protected DisciplinaService(DisciplinaRepository repository) {
        super(repository, DisciplinaRequestDTO::toModel, DisciplinaResponseDTO::new);
    }

//    public List<AtividadeModeloResponseDTO> listarAtividadesDisciplina(String disciplinaId){
//        return atividadeService.findByDisciplina(disciplinaId);
//    }
}
