package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Assinante;
import io.github.raphaelmuniz.uniflow.entities.AtividadeAssinante;
import io.github.raphaelmuniz.uniflow.entities.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtividadeAssinanteService extends GenericCrudServiceImpl<AtividadeAssinanteRequestDTO, AtividadeAssinanteResponseDTO, AtividadeAssinante, String> {
    @Autowired
    AtividadeAssinanteRepository repository;

    @Autowired
    AssinanteRepository assinanteRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Override
    public AtividadeAssinanteResponseDTO create(AtividadeAssinanteRequestDTO data) {
        Assinante dono = assinanteRepository.findById(data.getAssinanteDonoId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        AtividadeAssinante novaAtividade = new AtividadeAssinante(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, null, data.getStatusEntrega(), dono);
        AtividadeAssinante saved = repository.save(novaAtividade);
        return new AtividadeAssinanteResponseDTO(saved);
    }

    protected AtividadeAssinanteService(AtividadeAssinanteRepository repository) {
        super(repository, AtividadeAssinanteRequestDTO::toModel, AtividadeAssinanteResponseDTO::new);
    }
}
