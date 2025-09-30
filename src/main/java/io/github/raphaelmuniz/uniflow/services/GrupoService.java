package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AdicionarMembroGrupoDTO;
import io.github.raphaelmuniz.uniflow.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResumeResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class GrupoService extends GenericCrudServiceImpl<GrupoRequestDTO, GrupoResponseDTO, Grupo, String> {
    @Autowired
    GrupoRepository repository;

    @Autowired
    AssinanteRepository assinanteRepository;

    @Autowired
    EstudanteRepository estudanteRepository;

    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    protected GrupoService(GrupoRepository repository) {
        super(repository, GrupoRequestDTO::toModel, GrupoResponseDTO::new);
    }

    @Override
    public GrupoResponseDTO create(GrupoRequestDTO data) {
        if (data.getEstudantesInscritosId().isEmpty()) {
            throw new ConstraintViolationException("É necessário ao menos um estudante inscrito.", Set.of());
        }

        List<Estudante> estudantesInscritos = estudanteRepository.findAllById(data.getEstudantesInscritosId());
        if (estudantesInscritos.size() != data.getEstudantesInscritosId().size()) {
            throw new NotFoundException("Algum estudante inscrito não encontrado.");
        }

        Assinante criador = assinanteRepository.findById(data.getCriadorId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));

        Grupo novoGrupo = new Grupo();
        novoGrupo.setTitulo(data.getTitulo());
        novoGrupo.setDescricao(data.getDescricao());
        novoGrupo.setTipoGrupo(data.getTipoGrupo());
        novoGrupo.setCriador(criador);

        estudantesInscritos.forEach(estudante -> {
            InscricaoGrupo inscricao = new InscricaoGrupo(null, LocalDateTime.now(), PapelGrupoEnum.MEMBRO, null, estudante);
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
        Estudante estudanteEncontrado = estudanteRepository.findById(data.getEstudanteId()).orElseThrow(() -> new NotFoundException("Estudante não encontrado."));
        InscricaoGrupo inscricaoGrupo = new InscricaoGrupo(null, LocalDateTime.now(), data.getPapel(), grupo, estudanteEncontrado);
        grupo.addInscricao(inscricaoGrupo);
    }

    @Transactional
    public void removerIntegrante(String grupoId, String membroId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        InscricaoGrupo inscricaoEncontrada = inscricaoGrupoRepository.findByGrupo_IdAndEstudanteMembro_Id(grupoId, membroId).orElseThrow(() -> new NotFoundException("Inscrição não encontrada."));
        grupo.removeInscricao(inscricaoEncontrada);
    }

    public List<AssinanteResumeResponseDTO> listarMembros(String grupoId) {
        Grupo grupo = repository.findByIdWithMembros(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getInscricoes().stream()
                .map(InscricaoGrupo::getEstudanteMembro)
                .map(AssinanteResumeResponseDTO::new)
                .toList();
    }

    public List<AtividadeGrupoResponseDTO> listarAtividades(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getAtividadesPublicadas().stream().map(AtividadeGrupoResponseDTO::new).toList();
    }
}
