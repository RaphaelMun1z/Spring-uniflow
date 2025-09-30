package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.PapelRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PapelService extends GenericCrudServiceImpl<PapelRequestDTO, PapelResponseDTO, Papel, String> {
    @Autowired
    PapelRepository papelRepository;

    protected PapelService(PapelRepository repository) {
        super(repository, PapelRequestDTO::toModel, PapelResponseDTO::new);
    }

    public Papel findByNome(String nome) {
        return papelRepository.findByNome(nome).orElseThrow(() -> new NotFoundException("Papel não encontrado."));
    }
}
