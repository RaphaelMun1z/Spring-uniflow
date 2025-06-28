package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteEmGrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.repositories.AssinanteRepository;
import io.github.raphaelmuniz.plansFy.repositories.AtividadeModeloRepository;
import io.github.raphaelmuniz.plansFy.repositories.GrupoRepository;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.InscricaoGrupoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GrupoService {
    @Autowired
    GrupoRepository repository;

    @Autowired
    AssinanteRepository assinanteRepository;

    @Autowired
    AtividadeModeloRepository atividadeModeloRepository;

    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    @Transactional
    public GrupoResponseDTO create(GrupoRequestDTO data) {
        // Declara o grupo
        Grupo grupo = new Grupo(null, data.getTitulo(), data.getDescricao(), null, null);

        // Encontra os assinantes
        List<Assinante> assinantes = assinanteRepository.findAllById(data.getInscritosId());

        // Declara as inscrições
        Set<InscricaoGrupo> inscricoes = new HashSet<>();

        assinantes.forEach(assinante -> {
            InscricaoGrupo inscricaoGrupo = new InscricaoGrupo(null, grupo, assinante, LocalDateTime.now(), "Padrão");
            inscricoes.add(inscricaoGrupo);
        });

        grupo.setInscritos(inscricoes);

        Grupo saved = repository.save(grupo);
        return new GrupoResponseDTO(saved);
    }

    public List<GrupoResponseDTO> findAll() {
        return repository.findAll().stream().map(GrupoResponseDTO::new).collect(Collectors.toList());
    }

    public GrupoResponseDTO findById(String id) {
        Grupo created = repository.findById(id).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return new GrupoResponseDTO(created);
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

//    @Transactional
//    public void adicionarAluno(@RequestBody @Valid EstudanteEmGrupoRequestDTO data) {
//        Grupo grupoEncontrado = findById(data.getGrupoId()).toModel();
//        Estudante estudanteEncontrado = assinanteRepository.findById(data.getAlunoId()).orElseThrow(() -> new NotFoundException("Aluno não encontrado."));
//
//        if (!grupoEncontrado.getEstudantes().contains(estudanteEncontrado)) {
//            grupoEncontrado.getEstudantes().add(estudanteEncontrado);
//            repository.save(grupoEncontrado);
//        }
//    }
//
//    @Transactional
//    public void removerAluno(@RequestBody @Valid EstudanteEmGrupoRequestDTO data) {
//        Grupo grupoEncontrado = findById(data.getGrupoId()).toModel();
//        Estudante estudanteEncontrado = assinanteRepository.findById(data.getAlunoId()).orElseThrow(() -> new NotFoundException("Aluno não encontrado."));
//
//        if (grupoEncontrado.getEstudantes().contains(estudanteEncontrado)) {
//            grupoEncontrado.getEstudantes().remove(estudanteEncontrado);
//            repository.save(grupoEncontrado);
//        }
//    }
//
    public List<GrupoResponseDTO> findGruposByAlunoId(String alunoId) {
        return repository.findByInscritos_Id(alunoId).stream().map(GrupoResponseDTO::new).collect(Collectors.toList());
    }
}
