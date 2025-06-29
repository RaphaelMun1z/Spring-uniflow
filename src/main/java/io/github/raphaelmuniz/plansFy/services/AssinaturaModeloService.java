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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AssinaturaModeloService extends GenericCrudServiceImpl<AssinaturaModeloRequestDTO, AssinaturaModeloResponseDTO, AssinaturaModelo, String> {
    @Autowired
    AssinaturaModeloRepository repository;

    protected AssinaturaModeloService(AssinaturaModeloRepository repository) {
        super(repository, AssinaturaModeloRequestDTO::toModel, AssinaturaModeloResponseDTO::new);
    }

    @Transactional
    public AssinaturaModeloResponseDTO create(AssinaturaModeloRequestDTO data) {
        AssinaturaModelo saved = repository.save(data.toModel());
        return new AssinaturaModeloResponseDTO(saved);
    }
}
