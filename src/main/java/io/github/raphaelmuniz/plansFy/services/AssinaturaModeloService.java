package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.entities.Disciplina;
import io.github.raphaelmuniz.plansFy.entities.Professor;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinaturaModeloRepository;
import io.github.raphaelmuniz.plansFy.repositories.DisciplinaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssinaturaModeloService {
    @Autowired
    AssinaturaModeloRepository repository;

    @Transactional
    public AssinaturaModeloResponseDTO create(AssinaturaModeloRequestDTO data) {
        AssinaturaModelo saved = repository.save(data.toModel());
        return new AssinaturaModeloResponseDTO(saved);
    }

    public List<AssinaturaModeloResponseDTO> findAll() {
        return repository.findAll().stream().map(AssinaturaModeloResponseDTO::new).collect(Collectors.toList());
    }

    public AssinaturaModeloResponseDTO findById(String id) {
        AssinaturaModelo created = repository.findById(id).orElseThrow(() -> new NotFoundException("Assinatura não encontrada."));
        return new AssinaturaModeloResponseDTO(created);
    }

    @Transactional
    public void delete(String id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("Assinatura não encontrada.");
        }

        repository.deleteById(id);
    }
}
