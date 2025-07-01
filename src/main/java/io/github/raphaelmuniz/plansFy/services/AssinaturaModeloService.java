package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.repositories.AssinaturaModeloRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
