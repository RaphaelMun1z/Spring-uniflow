package io.github.raphaelmuniz.uniflow.services.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeEntregaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AvaliacaoAtividadeResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.atividade.AvaliacaoAtividade;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeEntregaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AvaliacaoAtividadeRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AtividadeEntregaService {
    private final AtividadeEntregaRepository atividadeEntregaRepository;
    private final AvaliacaoAtividadeRepository avaliacaoAtividadeRepository;

    protected AtividadeEntregaService(
            AtividadeEntregaRepository atividadeEntregaRepository,
            AvaliacaoAtividadeRepository avaliacaoAtividadeRepository
    ) {
        this.atividadeEntregaRepository = atividadeEntregaRepository;
        this.avaliacaoAtividadeRepository = avaliacaoAtividadeRepository;
    }

    @Transactional
    public void entregarAtividade(String entregaId, AtividadeEntregaRequestDTO dto, Estudante estudanteLogado) {
        AtividadeEntrega entrega = atividadeEntregaRepository.findById(entregaId)
                .orElseThrow(() -> new NotFoundException("Entrega de atividade não encontrada."));

        if (!entrega.getEstudanteDono().getId().equals(estudanteLogado.getId())) {
            throw new AccessDeniedException("Você não tem permissão para modificar esta entrega.");
        }

        if (entrega.getStatusEntrega() == StatusEntregaEnum.AVALIADO) {
            throw new BusinessException("Esta atividade já foi avaliada e não pode ser alterada.");
        }

        if (dto.status() != StatusEntregaEnum.ENTREGUE) {
            throw new BusinessException("Ação inválida. O status só pode ser alterado para ENTREGUE.");
        }

        entrega.setStatusEntrega(StatusEntregaEnum.ENTREGUE);
        entrega.setDataEnvio(LocalDateTime.now());
        entrega.setTextoResposta(dto.textoResposta());
        entrega.setAnexos(dto.anexos());

        atividadeEntregaRepository.save(entrega);
    }

    @Transactional
    public AvaliacaoAtividadeResponseDTO avaliarEntrega(String entregaId, AvaliacaoRequestDTO dto, Professor professorLogado) {
        AtividadeEntrega entrega = atividadeEntregaRepository.findById(entregaId)
                .orElseThrow(() -> new NotFoundException("Entrega de atividade não encontrada."));

        String idProfessorCriador = entrega.getAtividadeAvaliativaOrigem().getAssinanteCriadorAtividade().getId();
        if (!idProfessorCriador.equals(professorLogado.getId())) {
            throw new AccessDeniedException("Apenas o professor que criou a atividade pode avaliá-la.");
        }

        if (entrega.getStatusEntrega() != StatusEntregaEnum.ENTREGUE) {
            throw new BusinessException("Esta atividade não pode ser avaliada pois seu status é '" + entrega.getStatusEntrega() + "'.");
        }

        AvaliacaoAtividade avaliacao = entrega.getAvaliacaoAtividade() != null ? entrega.getAvaliacaoAtividade() : new AvaliacaoAtividade();

        avaliacao.setNota(dto.nota());
        avaliacao.setFeedback(dto.feedback());
        avaliacao.setProfessorAvaliador(professorLogado);
        avaliacao.setDataAvaliacao(LocalDateTime.now());
        AvaliacaoAtividade avaliacaoSalva = avaliacaoAtividadeRepository.save(avaliacao);

        entrega.setAvaliacao(avaliacaoSalva);
        entrega.setStatusEntrega(StatusEntregaEnum.AVALIADO);
        atividadeEntregaRepository.save(entrega);

        return AvaliacaoAtividadeResponseDTO.fromEntity(avaliacaoSalva);
    }
}
