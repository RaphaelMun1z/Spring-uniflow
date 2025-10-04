package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.atividade.AvaliacaoAtividade;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public record AtividadeAvaliativaDetalhadaResponseDTO(
        String id,
        String titulo,
        String descricao,
        LocalDateTime dataLancamento,
        LocalDateTime prazoEntrega,
        Double notaMaxima,
        Boolean permiteEnvioAtrasado,
        DificuldadeEnum dificuldade,
        VisivibilidadeAtividadeEnum visibilidadeAtividade,
        List<EntregaResumoDTO> entregasDosAlunos
) {
    public record EntregaResumoDTO(
            String id,
            String alunoId,
            String alunoNome,
            StatusEntregaEnum status,
            LocalDateTime dataEnvio,
            Double nota
    ) {
    }

    public static AtividadeAvaliativaDetalhadaResponseDTO fromEntity(AtividadeAvaliativa atividade) {
        List<EntregaResumoDTO> entregas = new ArrayList<>();

        if (atividade.getCopiasDosAssinantes() != null) {
            entregas = atividade.getCopiasDosAssinantes().stream()
                    .map(entrega -> {
                        Double nota = Optional.ofNullable(entrega.getAvaliacaoAtividade())
                                .map(AvaliacaoAtividade::getNota)
                                .orElse(null);

                        return new EntregaResumoDTO(
                                entrega.getId(),
                                entrega.getEstudanteDono().getId(),
                                entrega.getEstudanteDono().getNome(),
                                entrega.getStatusEntrega(),
                                entrega.getDataEnvio(),
                                nota
                        );
                    })
                    .collect(Collectors.toList());
        }

        return new AtividadeAvaliativaDetalhadaResponseDTO(
                atividade.getId(),
                atividade.getTitulo(),
                atividade.getDescricao(),
                atividade.getDataLancamento(),
                atividade.getPrazoEntrega(),
                atividade.getNotaMaxima(),
                atividade.getPermiteEnvioAtrasado(),
                atividade.getDificuldade(),
                atividade.getVisivibilidadeAtividade(),
                entregas
        );
    }
}