package io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.atividadeStrategy;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.AdicionarAtividadeRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeColaborativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.TarefaStatusMembro;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeColaborativaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.TarefaStatusMembroRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class GrupoEstudoAtividadeStrategy implements AtividadeStrategy {

    private final AtividadeColaborativaRepository atividadeColaborativaRepository;
    private final TarefaStatusMembroRepository tarefaStatusMembroRepository;

    public GrupoEstudoAtividadeStrategy(
            AtividadeColaborativaRepository atividadeColaborativaRepository,
            TarefaStatusMembroRepository tarefaStatusMembroRepository) {
        this.atividadeColaborativaRepository = atividadeColaborativaRepository;
        this.tarefaStatusMembroRepository = tarefaStatusMembroRepository;
    }

    @Override
    public TipoGrupoEnum supports() {
        return TipoGrupoEnum.GRUPO_ESTUDO;
    }

    @Override
    @Transactional
    public void adicionarAtividade(Grupo grupo, AdicionarAtividadeRequestDTO dto, Usuario usuarioLogado) {
        if (dto.tipo() != AdicionarAtividadeRequestDTO.TipoAtividade.COLABORATIVA || dto.colaborativa() == null) {
            throw new BusinessException("Requisição inválida para adicionar atividade em um GRUPO_ESTUDO.");
        }

        grupo.getInscricoes().stream()
                .filter(i -> i.getMembro().getId().equals(usuarioLogado.getId()))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("Você não é membro deste grupo de estudo."));

        AtividadeColaborativa novaAtividade = new AtividadeColaborativa();
        novaAtividade.setTitulo(dto.colaborativa().titulo());
        novaAtividade.setDescricao(dto.colaborativa().descricao());
        novaAtividade.setGrupoPublicado(grupo);
        novaAtividade.setCriador((Estudante) usuarioLogado);
        AtividadeColaborativa atividadeSalva = atividadeColaborativaRepository.save(novaAtividade);

        List<TarefaStatusMembro> tarefas = grupo.getInscricoes().stream().map(inscricao -> {
            TarefaStatusMembro tarefa = new TarefaStatusMembro();
            tarefa.setAtividadeColaborativa(atividadeSalva);
            tarefa.setEstudanteDono((Estudante) inscricao.getMembro());
            tarefa.setStatusEntrega(StatusEntregaEnum.PENDENTE);
            return tarefa;
        }).toList();

        tarefaStatusMembroRepository.saveAll(tarefas);
    }
}