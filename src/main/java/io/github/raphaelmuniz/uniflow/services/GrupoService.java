package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AdicionarMembroGrupoDTO;
import io.github.raphaelmuniz.uniflow.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResponseDTO;
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

        Grupo grupo = new Grupo(null, data.getTitulo(), data.getDescricao(), null, null);

        List<Assinante> assinantes = assinanteRepository.findAllById(data.getInscritosId());
        if (assinantes.size() != data.getInscritosId().size()) {
            throw new NotFoundException("Algum inscrito não encontrado.");
        }

        Grupo grupoSalvo = repository.save(grupo);

        Set<InscricaoGrupo> inscricoesGrupo = new HashSet<>();
        assinantes.forEach(assinante -> {
            InscricaoGrupo inscricao = new InscricaoGrupo(null, LocalDateTime.now(), PapelGrupoEnum.MEMBRO, grupoSalvo, assinante);
            InscricaoGrupo inscSalva = inscricaoGrupoRepository.save(inscricao);
            inscricoesGrupo.add(inscSalva);
        });
        grupoSalvo.setInscricoes(inscricoesGrupo);

        List<AtividadeGrupo> atividadesGrupo = new ArrayList<>();
        data.getAtividadesGrupo().forEach(atvGrupo -> {
            AtividadeGrupo atvGrupoSalvo = atividadeGrupoRepository.save(atvGrupo);
            atividadesGrupo.add(atvGrupoSalvo);
        });
        grupo.setAtividadesPublicadas(atividadesGrupo);

        Grupo grupoAtualizado = repository.save(grupoSalvo);
        return new GrupoResponseDTO(grupoAtualizado);
    }

    @Transactional
    public void adicionarIntegrantes(AdicionarMembroGrupoDTO data) {
        Grupo grupo = repository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        Assinante assinanteEncontrado = assinanteRepository.findById(data.getIntegranteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        InscricaoGrupo inscricaoGrupo = new InscricaoGrupo(null, LocalDateTime.now(), data.getPapel(), grupo, assinanteEncontrado);
        inscricaoGrupoRepository.save(inscricaoGrupo);
    }

    @Transactional
    public void removerIntegrante(String grupoId, String membroId) {
        InscricaoGrupo inscricaoEncontrada = inscricaoGrupoRepository.findByGrupo_IdAndMembro_Id(grupoId, membroId).orElseThrow(() -> new NotFoundException("Inscrição não encontrada."));
        inscricaoGrupoRepository.delete(inscricaoEncontrada);
    }

    public List<AssinanteResponseDTO> listarMembros(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        List<AssinanteResponseDTO> membros = grupo.getInscricoes().stream().map(InscricaoGrupo::getMembro).map(AssinanteResponseDTO::new).toList();
        return membros;
    }

    public List<AtividadeGrupoResponseDTO> listarAtividades(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        List<AtividadeGrupoResponseDTO> atividades = grupo.getAtividadesPublicadas().stream().map(AtividadeGrupoResponseDTO::new).toList();
        return atividades;
    }
}
