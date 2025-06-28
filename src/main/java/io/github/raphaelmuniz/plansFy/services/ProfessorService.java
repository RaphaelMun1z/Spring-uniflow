package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.ProfessorRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Professor;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService {
    @Autowired
    ProfessorRepository repository;

    @Transactional
    public ProfessorResponseDTO create(ProfessorRequestDTO data) {
        Professor saved = repository.save(data.toModel());
        return new ProfessorResponseDTO(saved);
    }

    public List<ProfessorResponseDTO> findAll() {
        return repository.findAll().stream().map(ProfessorResponseDTO::new).collect(Collectors.toList());
    }

    public ProfessorResponseDTO findById(String id) {
        Professor created = repository.findById(id).orElseThrow(() -> new NotFoundException("Aluno não encontrado."));
        return new ProfessorResponseDTO(created);
    }

    @Transactional
    public void delete(String id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("Professor não encontrado.");
        }
        repository.deleteById(id);
    }
}
