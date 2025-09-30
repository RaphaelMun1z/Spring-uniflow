package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AtividadeGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtividadeGrupoService extends GenericCrudServiceImpl<AtividadeGrupoRequestDTO, AtividadeGrupoResponseDTO, AtividadeGrupo, String> {
    @Autowired
    AtividadeGrupoRepository repository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    AssinanteRepository assinanteRepository;

    protected AtividadeGrupoService(AtividadeGrupoRepository repository) {
        super(repository, AtividadeGrupoRequestDTO::toModel, AtividadeGrupoResponseDTO::new);
    }

    @Override
    public AtividadeGrupoResponseDTO create(AtividadeGrupoRequestDTO data) {
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        Assinante criador = assinanteRepository.findById(data.getCriadorId()).orElseThrow(() -> new NotFoundException("Criador não encontrado."));
        Grupo grupo = grupoRepository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        AtividadeGrupo atividadeGrupo = new AtividadeGrupo(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, null, data.getVisivibilidade(), grupo, criador);

        AtividadeGrupo saved = repository.save(atividadeGrupo);
        return new AtividadeGrupoResponseDTO(saved);
    }
}
