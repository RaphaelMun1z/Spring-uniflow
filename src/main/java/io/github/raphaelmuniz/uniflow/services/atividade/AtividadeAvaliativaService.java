package io.github.raphaelmuniz.uniflow.services.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeAvaliativaRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtividadeAvaliativaService extends GenericCrudServiceImpl<AtividadeAvaliativaRequestDTO, AtividadeAvaliativaResponseDTO, AtividadeAvaliativa, String> {
    @Autowired
    AtividadeAvaliativaRepository repository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    AssinanteRepository assinanteRepository;

    protected AtividadeAvaliativaService(AtividadeAvaliativaRepository repository) {
        super(repository, AtividadeAvaliativaRequestDTO::toModel, AtividadeAvaliativaResponseDTO::new);
    }

    @Override
    public AtividadeAvaliativaResponseDTO create(AtividadeAvaliativaRequestDTO data) {
        Assinante criador = assinanteRepository.findById(data.getCriadorId()).orElseThrow(() -> new NotFoundException("Criador não encontrado."));
        Grupo grupo = grupoRepository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        AtividadeAvaliativa atividadeAvaliativa = new AtividadeAvaliativa(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), null, data.getVisivibilidade(), grupo, criador);

        AtividadeAvaliativa saved = repository.save(atividadeAvaliativa);
        return new AtividadeAvaliativaResponseDTO(saved);
    }
}
