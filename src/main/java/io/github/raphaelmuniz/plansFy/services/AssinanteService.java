package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AssinanteRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.repositories.AssinanteRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AssinanteService extends GenericCrudServiceImpl<AssinanteRequestDTO, AssinanteResponseDTO, Assinante, String> {
    protected AssinanteService(AssinanteRepository repository) {
        super(repository, null, AssinanteResponseDTO::new);
    }

    @Override
    public AssinanteResponseDTO create(AssinanteRequestDTO data) {
        throw new IllegalArgumentException();
    }
}
