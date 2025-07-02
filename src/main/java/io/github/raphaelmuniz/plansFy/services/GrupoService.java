package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AdicionarMembrosGrupoDTO;
import io.github.raphaelmuniz.plansFy.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.plansFy.repositories.*;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
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

    @Transactional
    public void adicionarIntegrantes(AdicionarMembrosGrupoDTO data) {
        Grupo grupo = repository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        data.getIntegrantesId().forEach(intId -> {
            Assinante assinanteEncontrado = assinanteRepository.findById(intId).orElseThrow(() -> new NotFoundException("Integrante não encontrado."));
            InscricaoGrupo inscricaoGrupo = new InscricaoGrupo(null, grupo, assinanteEncontrado, LocalDateTime.now(), "Padrão");
            inscricaoGrupoRepository.save(inscricaoGrupo);
        });
    }

    @Transactional
    public void removerIntegrantes(List<String> integrantesId, String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        integrantesId.forEach(intId -> {
            InscricaoGrupo inscricaoEncontrada = inscricaoGrupoRepository.findByGrupo_IdAndInscrito_Id(grupo.getId(), intId).orElseThrow(() -> new NotFoundException("Inscrição não encontrada."));
            inscricaoGrupoRepository.delete(inscricaoEncontrada);
        });
    }

    public List<Assinante> listarMembros(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        List<Assinante> membros = grupo.getInscritos().stream().map(InscricaoGrupo::getInscrito).toList();
        return membros;
    }

    @Transactional
    public void atribuirAtividadesAoGrupo(String grupoId, List<String> atividadesModeloId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        atividadesModeloId.forEach(atvModeloId -> {
            AtividadeModelo atividadeModelo = atividadeModeloRepository.findById(atvModeloId).orElseThrow(() -> new NotFoundException("Atividade modelo não encontrada."));
            AtividadeCopia atividadeCopia = new AtividadeCopia(atividadeModelo.getDataLancamento(), atividadeModelo.getPrazoEntrega(), atividadeModelo.getTitulo(), atividadeModelo.getDescricao(), atividadeModelo.getDificuldade(), atividadeModelo.getDisciplina(), null, StatusEntregaEnum.PENDENTE, grupo, null);
            atividadeCopiaRepository.save(atividadeCopia);
        });
    }

    public List<AtividadeCopia> listarAtividadesDoGrupo(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getAtividades();
    }
}
