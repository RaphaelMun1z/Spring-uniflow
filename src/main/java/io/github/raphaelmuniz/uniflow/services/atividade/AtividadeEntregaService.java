package io.github.raphaelmuniz.uniflow.services.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeEntregaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeEntregaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AvaliacaoAtividadeResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeAvaliativaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeEntregaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.EstudanteRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeEntregaService extends GenericCrudServiceImpl<AtividadeEntregaRequestDTO, AtividadeEntregaResponseDTO, AtividadeEntrega, String> {
    @Autowired
    AtividadeEntregaRepository repository;

    @Autowired
    EstudanteRepository estudanteRepository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    AtividadeAvaliativaRepository atividadeAvaliativaRepository;

    protected AtividadeEntregaService(AtividadeEntregaRepository repository) {
        super(repository, AtividadeEntregaRequestDTO::toModel, AtividadeEntregaResponseDTO::new);
    }

    @Override
    public AtividadeEntregaResponseDTO create(AtividadeEntregaRequestDTO data) {
        Estudante estudanteDono = estudanteRepository.findById(data.getEstudanteDonoId()).orElseThrow(() -> new NotFoundException("Estudante não encontrado."));
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        AtividadeEntrega novaAtividade = new AtividadeEntrega(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, null, data.getStatusEntrega(), estudanteDono, null);
        AtividadeEntrega saved = repository.save(novaAtividade);
        return new AtividadeEntregaResponseDTO(saved);
    }

    public void entregarTarefa(String atividadeId) {
        AtividadeEntrega atividadeEntrega = repository.findById(atividadeId).orElseThrow(() -> new NotFoundException("Atividade não encontrada."));
        if (atividadeEntrega.getStatusEntrega() == StatusEntregaEnum.ENTREGUE) {
            throw new BusinessException("A atividade já foi entregue!");
        }

        if (LocalDateTime.now().isAfter(atividadeEntrega.getPrazoEntrega())) {
            atividadeEntrega.setStatusEntrega(StatusEntregaEnum.ENTREGUE_COM_ATRASO);
        } else {
            atividadeEntrega.setStatusEntrega(StatusEntregaEnum.ENTREGUE);
        }

        repository.save(atividadeEntrega);
    }

    public List<AtividadeEntregaResponseDTO> findClonedActivitiesByGroup(String assinanteId, String grupoId, StatusEntregaEnum status) {
        List<AtividadeEntrega> atividades;
        if (status != null) {
            atividades = repository.findAllByEstudanteAndGrupoOrigemAndStatus(assinanteId, grupoId, status);
        } else {
            atividades = repository.findAllByEstudanteAndGrupoOrigem(assinanteId, grupoId);
        }

        return atividades.stream()
                .map(AtividadeEntregaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public AtividadeEntregaResponseDTO clonarAtividadeAvaliativa(String estudanteDonoId, String atividadeAvaliativaId) {
        boolean jaExisteCopia = repository.existsByEstudanteDonoIdAndAtividadeAvaliativaOrigemId(estudanteDonoId, atividadeAvaliativaId);
        if (jaExisteCopia) {
            throw new BusinessException("Você já iniciou esta atividade.");
        }

        Estudante estudanteDono = estudanteRepository.findById(estudanteDonoId).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        AtividadeAvaliativa ag = atividadeAvaliativaRepository.findById(atividadeAvaliativaId).orElseThrow(() -> new NotFoundException("Atividade grupo não encontrado."));
        AtividadeEntrega copia = new AtividadeEntrega(LocalDateTime.now(), ag.getPrazoEntrega(), ag.getTitulo(), ag.getDescricao(), ag.getDificuldade(), ag.getDisciplina(), null, StatusEntregaEnum.PENDENTE, estudanteDono, ag);
        AtividadeEntrega copiaSalva = repository.save(copia);
        return new AtividadeEntregaResponseDTO(copiaSalva);
    }

    public List<AtividadeEntregaResponseDTO> findByEstudanteDonoId(String estudanteDonoId) {
        return repository.findByEstudanteDonoId(estudanteDonoId).stream().map(AtividadeEntregaResponseDTO::new).toList();
    }

    public void atualizarStatus(Usuario usuarioLogado, String atividadeId, StatusEntregaEnum novoStatus) {
        AtividadeEntrega atividade = repository.findById(atividadeId)
                .orElseThrow(() -> new NotFoundException("Atividade não encontrada com o ID: " + atividadeId));

        if (!atividade.getEstudanteDono().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Você não tem permissão para alterar esta atividade.");
        }

        if (atividade.getNota() != null) {
            throw new BusinessException("Não é possível alterar o status de uma atividade já avaliada.");
        }

        atividade.setStatusEntrega(novoStatus);
        repository.save(atividade);
    }

    @Transactional
    public AvaliacaoAtividadeResponseDTO avaliarAtividade(String atividadeId, AtividadeAvaliacaoRequestDTO avaliacaoDTO) {
        AtividadeEntrega atividade = repository.findById(atividadeId)
                .orElseThrow(() -> new NotFoundException("Atividade do assinante não encontrada."));

        if (atividade.getStatusEntrega() != StatusEntregaEnum.ENTREGUE) {
            throw new BusinessException("Só é possível avaliar atividades com o status 'ENTREGUE'.");
        }

        atividade.setNota(avaliacaoDTO.getNota());
        atividade.setFeedback(avaliacaoDTO.getFeedback());
        //atividade.setDataAvaliacao(LocalDateTime.now());
        atividade.setStatusEntrega(StatusEntregaEnum.AVALIADO);

        AtividadeEntrega atividadeAvaliada = repository.save(atividade);

        AvaliacaoAtividadeResponseDTO atvAvaliadaDTO = new AvaliacaoAtividadeResponseDTO();
        atividadeAvaliada.setId(atividade.getId());
        atividadeAvaliada.setTitulo(atividade.getTitulo());
        atividadeAvaliada.setDescricao(atividade.getDescricao());
        atividadeAvaliada.setNota(atividade.getNota());
        atividadeAvaliada.setFeedback(atividade.getFeedback());

        return atvAvaliadaDTO;
    }

    public List<AtividadeEntregaResponseDTO> atividadesClonadasDeAtividadeAvaliativa(String atividadeAvaliativaOrigemId) {
        List<AtividadeEntrega> atividades = repository.findByAtividadeAvaliativaOrigemId(atividadeAvaliativaOrigemId);
        return atividades.stream().map(AtividadeEntregaResponseDTO::new).toList();
    }

}
