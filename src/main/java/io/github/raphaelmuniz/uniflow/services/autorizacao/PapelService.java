package io.github.raphaelmuniz.uniflow.services.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PapelRepository;
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
