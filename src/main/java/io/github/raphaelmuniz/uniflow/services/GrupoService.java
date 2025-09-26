package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AdicionarMembroGrupoDTO;
import io.github.raphaelmuniz.uniflow.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResumeResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GrupoService extends GenericCrudServiceImpl<GrupoRequestDTO, GrupoResponseDTO, Grupo, String> {
    @Autowired
    GrupoRepository repository;

    @Autowired
    AssinanteRepository assinanteRepository;

    @Autowired
    AtividadeGrupoRepository atividadeGrupoRepository;

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

        List<Assinante> assinantes = assinanteRepository.findAllById(data.getInscritosId());
        if (assinantes.size() != data.getInscritosId().size()) {
            throw new NotFoundException("Algum inscrito não encontrado.");
        }

        Assinante criador = assinanteRepository.findById(data.getCriadorId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));

        Grupo novoGrupo = new Grupo();
        novoGrupo.setTitulo(data.getTitulo());
        novoGrupo.setDescricao(data.getDescricao());
        novoGrupo.setCriador(criador);

        assinantes.forEach(assinante -> {
            InscricaoGrupo inscricao = new InscricaoGrupo(null, LocalDateTime.now(), PapelGrupoEnum.MEMBRO, null, assinante);
            novoGrupo.addInscricao(inscricao);
        });

        if (data.getAtividadesGrupo() != null && !data.getAtividadesGrupo().isEmpty()) {
            data.getAtividadesGrupo().forEach(atividade -> {
                atividade.setGrupoPublicado(novoGrupo);
                atividade.setCriadorAtividade(criador);
                novoGrupo.getAtividadesPublicadas().add(atividade);
            });
        }

        Grupo grupoSalvo = repository.save(novoGrupo);

        return new GrupoResponseDTO(grupoSalvo);
    }

    @Transactional
    public void adicionarIntegrantes(AdicionarMembroGrupoDTO data) {
        Grupo grupo = repository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        Assinante assinanteEncontrado = assinanteRepository.findById(data.getIntegranteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        InscricaoGrupo inscricaoGrupo = new InscricaoGrupo(null, LocalDateTime.now(), data.getPapel(), grupo, assinanteEncontrado);
        grupo.addInscricao(inscricaoGrupo);
    }

    @Transactional
    public void removerIntegrante(String grupoId, String membroId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        InscricaoGrupo inscricaoEncontrada = inscricaoGrupoRepository.findByGrupo_IdAndMembro_Id(grupoId, membroId).orElseThrow(() -> new NotFoundException("Inscrição não encontrada."));
        grupo.removeInscricao(inscricaoEncontrada);
    }

    public List<AssinanteResumeResponseDTO> listarMembros(String grupoId) {
        Grupo grupo = repository.findByIdComMembros(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getInscricoes().stream()
                .map(InscricaoGrupo::getMembro)
                .map(AssinanteResumeResponseDTO::new)
                .toList();
    }

    public List<AtividadeGrupoResponseDTO> listarAtividades(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        List<AtividadeGrupoResponseDTO> atividades = grupo.getAtividadesPublicadas().stream().map(AtividadeGrupoResponseDTO::new).toList();
        return atividades;
    }
}
