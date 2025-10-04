package io.github.raphaelmuniz.uniflow.services.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.TarefaStatusUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.TarefaStatusMembro;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.atividade.TarefaStatusMembroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AtividadeColaborativaService {

    private final TarefaStatusMembroRepository tarefaStatusMembroRepository;

    public AtividadeColaborativaService(TarefaStatusMembroRepository tarefaStatusMembroRepository) {
        this.tarefaStatusMembroRepository = tarefaStatusMembroRepository;
    }

    @Transactional
    public void atualizarMeuStatus(String atividadeId, TarefaStatusUpdateRequestDTO dto, Usuario usuarioLogado) {
        TarefaStatusMembro tarefa = tarefaStatusMembroRepository
                .findByAtividadeColaborativa_IdAndMembro_Id(atividadeId, usuarioLogado.getId())
                .orElseThrow(() -> new NotFoundException("Sua tarefa para esta atividade não foi encontrada. Verifique se você é membro do grupo."));

        tarefa.setStatus(dto.novoStatus());
        tarefa.setDataConclusao(LocalDateTime.now());

        tarefaStatusMembroRepository.save(tarefa);
    }
}