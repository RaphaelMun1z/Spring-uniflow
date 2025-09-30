package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.AtividadeEstudante;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AtividadeEstudanteRequestDTO {
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private String disciplinaId;
    private StatusEntregaEnum statusEntrega;
    private String estudanteDonoId;

    public AtividadeEstudante toModel() {
        return new AtividadeEstudante(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, null, statusEntrega, null, null);
    }
}
