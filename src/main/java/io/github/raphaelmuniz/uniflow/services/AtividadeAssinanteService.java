package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.exceptions.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeAssinanteService extends GenericCrudServiceImpl<AtividadeAssinanteRequestDTO, AtividadeAssinanteResponseDTO, AtividadeAssinante, String> {
    @Autowired
    AtividadeAssinanteRepository repository;

    @Autowired
    AssinanteRepository assinanteRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    AtividadeGrupoRepository atividadeGrupoRepository;

    protected AtividadeAssinanteService(AtividadeAssinanteRepository repository) {
        super(repository, AtividadeAssinanteRequestDTO::toModel, AtividadeAssinanteResponseDTO::new);
    }

    @Override
    public AtividadeAssinanteResponseDTO create(AtividadeAssinanteRequestDTO data) {
        Assinante dono = assinanteRepository.findById(data.getAssinanteDonoId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        AtividadeAssinante novaAtividade = new AtividadeAssinante(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, null, data.getStatusEntrega(), dono, null);
        AtividadeAssinante saved = repository.save(novaAtividade);
        return new AtividadeAssinanteResponseDTO(saved);
    }

    public void entregarTarefa(String atividadeId) {
        AtividadeAssinante atividadeAssinante = repository.findById(atividadeId).orElseThrow(() -> new NotFoundException("Atividade não encontrada."));
        if (atividadeAssinante.getStatusEntrega() == StatusEntregaEnum.ENTREGUE) {
            throw new BusinessException("A atividade já foi entregue!");
        }

        if (LocalDateTime.now().isAfter(atividadeAssinante.getPrazoEntrega())) {
            atividadeAssinante.setStatusEntrega(StatusEntregaEnum.ENTREGUE_COM_ATRASO);
        } else {
            atividadeAssinante.setStatusEntrega(StatusEntregaEnum.ENTREGUE);
        }

        repository.save(atividadeAssinante);
    }

    public List<AtividadeAssinanteResponseDTO> findClonedActivitiesByGroup(String assinanteId, String grupoId, StatusEntregaEnum status) {
        List<AtividadeAssinante> atividades;
        if (status != null) {
            atividades = repository.findAllByAssinanteAndGrupoOrigemAndStatus(assinanteId, grupoId, status);
        } else {
            atividades = repository.findAllByAssinanteAndGrupoOrigem(assinanteId, grupoId);
        }

        return atividades.stream()
                .map(AtividadeAssinanteResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AtividadeAssinanteResponseDTO clonarAtividadeGrupo(String assinanteId, String atividadeGrupoId) {
        boolean jaExisteCopia = repository.existsByAssinanteDonoIdAndAtividadeGrupoOrigemId(assinanteId, atividadeGrupoId);
        if (jaExisteCopia) {
            throw new BusinessException("Você já iniciou esta atividade.");
        }

        Assinante assinante = assinanteRepository.findById(assinanteId).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        AtividadeGrupo ag = atividadeGrupoRepository.findById(atividadeGrupoId).orElseThrow(() -> new NotFoundException("Atividade grupo não encontrado."));
        AtividadeAssinante copia = new AtividadeAssinante(LocalDateTime.now(), ag.getPrazoEntrega(), ag.getTitulo(), ag.getDescricao(), ag.getDificuldade(), ag.getDisciplina(), null, StatusEntregaEnum.PENDENTE, assinante, ag);
        AtividadeAssinante copiaSalva = repository.save(copia);
        return new AtividadeAssinanteResponseDTO(copiaSalva);
    }

    public List<AtividadeAssinanteResponseDTO> findByAssinanteDonoId(String assinanteId) {
        return repository.findByAssinanteDonoId(assinanteId).stream().map(AtividadeAssinanteResponseDTO::new).toList();
    }

}
