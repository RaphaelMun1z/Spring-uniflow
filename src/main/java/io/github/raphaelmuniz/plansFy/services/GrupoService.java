package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteEmGrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.plansFy.exceptions.BusinessException;
import io.github.raphaelmuniz.plansFy.repositories.*;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class GrupoService extends GenericCrudServiceImpl<GrupoRequestDTO, GrupoResponseDTO, Grupo, String> {
    @Autowired
    GrupoRepository repository;

    @Autowired
    AssinanteRepository assinanteRepository;

    @Autowired
    AtividadeModeloRepository atividadeModeloRepository;

    @Autowired
    AtividadeCopiaRepository atividadeCopiaRepository;

    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    protected GrupoService(GrupoRepository repository) {
        super(repository, GrupoRequestDTO::toModel, GrupoResponseDTO::new);
    }

    public GrupoResponseDTO create(GrupoRequestDTO data) {
        if (data.getInscritosId().isEmpty()) {
            throw new ConstraintViolationException("É necessário ao menos um inscrito.", Set.of());
        }

        Grupo grupo = new Grupo(null, data.getTitulo(), data.getDescricao(), null, null);

        List<Assinante> assinantes = assinanteRepository.findAllById(data.getInscritosId());
        if (assinantes.size() != data.getInscritosId().size()) {
            throw new NotFoundException("Algum inscrito não encontrado.");
        }

        List<AtividadeModelo> atividadesModelo = atividadeModeloRepository.findAllById(data.getAtividadesId());
        if (atividadesModelo.size() != data.getAtividadesId().size()) {
            throw new NotFoundException("Alguma atividade não encontrada.");
        }

        Grupo grupoSalvo = repository.save(grupo);

        Set<InscricaoGrupo> inscricoes = new HashSet<>();
        assinantes.forEach(assinante -> {
            InscricaoGrupo inscricao = new InscricaoGrupo(null, grupoSalvo, assinante, LocalDateTime.now(), "Padrão");
            InscricaoGrupo inscSalva = inscricaoGrupoRepository.save(inscricao);
            inscricoes.add(inscSalva);
        });
        grupoSalvo.setInscritos(inscricoes);

        List<AtividadeCopia> copias = new ArrayList<>();
        atividadesModelo.forEach(modelo -> {
            AtividadeCopia copia = new AtividadeCopia(LocalDateTime.now(), LocalDateTime.now(), modelo.getTitulo(), modelo.getDescricao(), modelo.getDificuldade(), modelo.getDisciplina(), null, StatusEntregaEnum.PENDENTE, grupoSalvo, null);
            AtividadeCopia copiaSalva = atividadeCopiaRepository.save(copia);
            copias.add(copiaSalva);
        });
        grupo.setAtividades(copias);

        Grupo grupoAtualizado = repository.save(grupoSalvo);
        return new GrupoResponseDTO(grupoAtualizado);
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
//    public List<GrupoResponseDTO> findGruposByAlunoId(String alunoId) {
//        return repository.findByInscritos_Id(alunoId).stream().map(GrupoResponseDTO::new).collect(Collectors.toList());
//    }
}
