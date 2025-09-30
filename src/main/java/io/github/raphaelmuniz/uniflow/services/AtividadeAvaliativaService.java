package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.*;
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
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        Assinante criador = assinanteRepository.findById(data.getCriadorId()).orElseThrow(() -> new NotFoundException("Criador não encontrado."));
        Grupo grupo = grupoRepository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        AtividadeAvaliativa atividadeAvaliativa = new AtividadeAvaliativa(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, null, data.getVisivibilidade(), grupo, criador);

        AtividadeAvaliativa saved = repository.save(atividadeAvaliativa);
        return new AtividadeAvaliativaResponseDTO(saved);
    }
}
