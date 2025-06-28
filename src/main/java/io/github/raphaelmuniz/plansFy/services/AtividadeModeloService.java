package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AtividadeModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AtividadeModelo;
import io.github.raphaelmuniz.plansFy.entities.Disciplina;
import io.github.raphaelmuniz.plansFy.repositories.AtividadeModeloRepository;
import io.github.raphaelmuniz.plansFy.repositories.DisciplinaRepository;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeModeloService {
    @Autowired
    AtividadeModeloRepository repository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Transactional
    public AtividadeModeloResponseDTO create(AtividadeModeloRequestDTO data) {
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));

        AtividadeModelo atividadeModelo = new AtividadeModelo(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, true);

        AtividadeModelo saved = repository.save(atividadeModelo);
        return new AtividadeModeloResponseDTO(saved);
    }

    public List<AtividadeModeloResponseDTO> findAll() {
        return repository.findAll().stream().map(AtividadeModeloResponseDTO::new).collect(Collectors.toList());
    }

    public AtividadeModeloResponseDTO findById(String id) {
        AtividadeModelo created = repository.findById(id).orElseThrow(() -> new NotFoundException("Atividade não encontrada."));
        return new AtividadeModeloResponseDTO(created);
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

//    public List<AtividadeModeloResponseDTO> findByDisciplina(String disciplinaId) {
//        Disciplina disciplina = disciplinaRepository.findById(disciplinaId).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
//
//        return repository.findByDisciplinaId(disciplina.getId()).stream().map(AtividadeModeloResponseDTO::new).collect(Collectors.toList());
//    }
}
