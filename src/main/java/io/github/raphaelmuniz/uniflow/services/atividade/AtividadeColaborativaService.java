package io.github.raphaelmuniz.uniflow.services.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeColaborativaUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.TarefaStatusUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeColaborativaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeColaborativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.TarefaStatusMembro;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeColaborativaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.TarefaStatusMembroRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtividadeColaborativaService {

    private final AtividadeColaborativaRepository atividadeColaborativaRepository;
    private final TarefaStatusMembroRepository tarefaStatusMembroRepository;

    public AtividadeColaborativaService(AtividadeColaborativaRepository atividadeColaborativaRepository, TarefaStatusMembroRepository tarefaStatusMembroRepository) {
        this.atividadeColaborativaRepository = atividadeColaborativaRepository;
        this.tarefaStatusMembroRepository = tarefaStatusMembroRepository;
    }

    @Transactional(readOnly = true)
    public AtividadeColaborativaDetalhadaResponseDTO buscarPorId(String id, Usuario usuarioLogado) {
        AtividadeColaborativa atividade = atividadeColaborativaRepository.findByIdWithStatusMembros(id)
            .orElseThrow(() -> new NotFoundException("Atividade colaborativa não encontrada."));

        boolean isMembro = atividade.getGrupo().getInscricoes().stream()
            .anyMatch(i -> i.getMembro().getId().equals(usuarioLogado.getId()));
        if (!isMembro) {
            throw new AccessDeniedException("Você não tem permissão para visualizar esta atividade.");
        }

        return new AtividadeColaborativaDetalhadaResponseDTO(atividade);
    }

    @Transactional
    public AtividadeColaborativaDetalhadaResponseDTO atualizar(String id, AtividadeColaborativaUpdateRequestDTO dto, Usuario usuarioLogado) {
        AtividadeColaborativa atividade = atividadeColaborativaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Atividade colaborativa não encontrada."));

        // Permissão: Apenas o criador da atividade pode editá-la.
        if (!atividade.getCriador().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Apenas o criador pode editar esta atividade.");
        }

        if (dto.titulo() != null) atividade.setTitulo(dto.titulo());
        if (dto.descricao() != null) atividade.setDescricao(dto.descricao());
        if (dto.prazoEntrega() != null) atividade.setPrazoEntrega(dto.prazoEntrega());
        if (dto.dificuldade() != null) atividade.setDificuldade(dto.dificuldade());

        AtividadeColaborativa atividadeAtualizada = atividadeColaborativaRepository.save(atividade);
        return new AtividadeColaborativaDetalhadaResponseDTO(atividadeAtualizada);
    }

    @Transactional
    public void deletar(String id, Usuario usuarioLogado) {
        AtividadeColaborativa atividade = atividadeColaborativaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Atividade colaborativa não encontrada."));

        // Permissão: Apenas o criador da atividade pode deletá-la.
        if (!atividade.getCriador().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Apenas o criador pode deletar esta atividade.");
        }

        atividadeColaborativaRepository.delete(atividade);
    }

    @Transactional
    public void atualizarMeuStatus(String atividadeId, TarefaStatusUpdateRequestDTO dto, Usuario usuarioLogado) {
        TarefaStatusMembro tarefa = tarefaStatusMembroRepository
            .findByAtividadeColaborativa_IdAndMembro_Id(atividadeId, usuarioLogado.getId())
            .orElseThrow(() -> new NotFoundException("Sua tarefa para esta atividade não foi encontrada."));

        tarefa.setStatus(dto.novoStatus());
        tarefaStatusMembroRepository.save(tarefa);
    }
}