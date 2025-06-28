package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.DisciplinaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.DisciplinaResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Disciplina;
import io.github.raphaelmuniz.plansFy.repositories.DisciplinaRepository;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisciplinaService {
    @Autowired
    DisciplinaRepository repository;

    @Autowired
    AtividadeModeloService atividadeService;

    @Transactional
    public DisciplinaResponseDTO create(DisciplinaRequestDTO data) {
        Disciplina saved = repository.save(data.toModel());
        return new DisciplinaResponseDTO(saved);
    }

    public List<DisciplinaResponseDTO> findAll() {
        return repository.findAll().stream().map(DisciplinaResponseDTO::new).collect(Collectors.toList());
    }

    public DisciplinaResponseDTO findById(String id) {
        Disciplina found = repository.findById(id).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        return new DisciplinaResponseDTO(found);
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

//    public List<AtividadeModeloResponseDTO> listarAtividadesDisciplina(String disciplinaId){
//        return atividadeService.findByDisciplina(disciplinaId);
//    }
}
