package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AtividadeCopiaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeCopiaResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinanteRepository;
import io.github.raphaelmuniz.plansFy.repositories.AtividadeCopiaRepository;
import io.github.raphaelmuniz.plansFy.repositories.DisciplinaRepository;
import io.github.raphaelmuniz.plansFy.repositories.GrupoRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AtividadeCopiaService extends GenericCrudServiceImpl<AtividadeCopiaRequestDTO, AtividadeCopiaResponseDTO, AtividadeCopia, String> {
    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    AssinanteRepository assinanteRepository;

    protected AtividadeCopiaService(AtividadeCopiaRepository repository) {
        super(repository, AtividadeCopiaRequestDTO::toModel, AtividadeCopiaResponseDTO::new);
    }

    @Override
    public AtividadeCopiaResponseDTO create(AtividadeCopiaRequestDTO data) {
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada"));
        AtividadeCopia atividadeCopia = new AtividadeCopia(LocalDateTime.now(), LocalDateTime.now(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, null, data.getStatus(), null, null);

        if (!data.getAssinanteId().isEmpty()) {
            Assinante assinante = assinanteRepository.findById(data.getAssinanteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado"));
            atividadeCopia.setAssinante(assinante);
        } else if (!data.getGrupoId().isEmpty()) {
            Grupo grupo = grupoRepository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado"));
            atividadeCopia.setGrupo(grupo);
        }

        AtividadeCopia saved = repository.save(atividadeCopia);
        return new AtividadeCopiaResponseDTO(saved);
    }
}
