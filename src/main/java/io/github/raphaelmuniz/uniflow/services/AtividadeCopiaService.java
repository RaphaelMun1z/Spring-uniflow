package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AtividadeCopiaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeCopiaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.AtividadeCopiaRepository;
import io.github.raphaelmuniz.uniflow.repositories.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.repositories.GrupoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
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
