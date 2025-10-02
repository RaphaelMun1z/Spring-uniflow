package io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.listarAtividadesStrategy;

import io.github.raphaelmuniz.uniflow.dto.res.grupo.AtividadeDoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeColaborativa;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeColaborativaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.TarefaStatusMembroRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListarAtividadesGrupoEstudoStrategy implements ListarAtividadesStrategy {

    private final AtividadeColaborativaRepository atividadeColaborativaRepository;
    private final TarefaStatusMembroRepository tarefaStatusMembroRepository;

    public ListarAtividadesGrupoEstudoStrategy(AtividadeColaborativaRepository atividadeColaborativaRepository, TarefaStatusMembroRepository tarefaStatusMembroRepository) {
        this.atividadeColaborativaRepository = atividadeColaborativaRepository;
        this.tarefaStatusMembroRepository = tarefaStatusMembroRepository;
    }

    @Override
    public TipoGrupoEnum supports() {
        return TipoGrupoEnum.GRUPO_ESTUDO;
    }

    @Override
    public List<AtividadeDoGrupoResponseDTO> listarAtividades(Grupo grupo, Usuario usuarioLogado) {
        boolean isMembro = grupo.getInscricoes().stream()
                .anyMatch(i -> i.getMembro().getId().equals(usuarioLogado.getId()));
        if (!isMembro) {
            throw new AccessDeniedException("Você não é membro deste grupo.");
        }

        List<AtividadeColaborativa> atividades = atividadeColaborativaRepository.findByGrupo_Id(grupo.getId());
        if (atividades.isEmpty()) {
            return Collections.emptyList();
        }

        int totalMembros = grupo.getInscricoes().size();

        return atividades.stream().map(atividade -> {
            int membrosConcluiram = tarefaStatusMembroRepository
                    .countByAtividadeColaborativa_IdAndStatus(atividade.getId(), StatusEntregaEnum.ENTREGUE.toString());

            return AtividadeDoGrupoResponseDTO.builder()
                    .id(atividade.getId())
                    .titulo(atividade.getTitulo())
                    .tipo("COLABORATIVA")
                    .resumoColaborativo(new AtividadeDoGrupoResponseDTO.ResumoColaborativo(membrosConcluiram, totalMembros))
                    .build();
        }).collect(Collectors.toList());
    }
}