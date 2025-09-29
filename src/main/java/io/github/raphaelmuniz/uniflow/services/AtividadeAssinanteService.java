package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AtividadeAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.profile.AtividadeAvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAvaliacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.exceptions.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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

    public void atualizarStatus(Usuario usuarioLogado, String atividadeId, StatusEntregaEnum novoStatus) {
        AtividadeAssinante atividade = repository.findById(atividadeId)
                .orElseThrow(() -> new NotFoundException("Atividade não encontrada com o ID: " + atividadeId));

        if (!atividade.getAssinanteDono().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Você não tem permissão para alterar esta atividade.");
        }

        if (atividade.getNota() != null) {
            throw new BusinessException("Não é possível alterar o status de uma atividade já avaliada.");
        }

        atividade.setStatusEntrega(novoStatus);
        repository.save(atividade);
    }

    @Transactional
    public AtividadeAvaliacaoResponseDTO avaliarAtividade(String atividadeId, AtividadeAvaliacaoRequestDTO avaliacaoDTO) {
        AtividadeAssinante atividade = repository.findById(atividadeId)
                .orElseThrow(() -> new NotFoundException("Atividade do assinante não encontrada."));

        if (atividade.getStatusEntrega() != StatusEntregaEnum.ENTREGUE) {
            throw new BusinessException("Só é possível avaliar atividades com o status 'ENTREGUE'.");
        }

        atividade.setNota(avaliacaoDTO.getNota());
        atividade.setFeedback(avaliacaoDTO.getFeedback());
        //atividade.setDataAvaliacao(LocalDateTime.now());
        atividade.setStatusEntrega(StatusEntregaEnum.AVALIADO);

        AtividadeAssinante atividadeAvaliada = repository.save(atividade);

        AtividadeAvaliacaoResponseDTO atvAvaliadaDTO = new AtividadeAvaliacaoResponseDTO();
        atividadeAvaliada.setId(atividade.getId());
        atividadeAvaliada.setTitulo(atividade.getTitulo());
        atividadeAvaliada.setDescricao(atividade.getDescricao());
        atividadeAvaliada.setNota(atividade.getNota());
        atividadeAvaliada.setFeedback(atividade.getFeedback());

        return atvAvaliadaDTO;
    }

}
