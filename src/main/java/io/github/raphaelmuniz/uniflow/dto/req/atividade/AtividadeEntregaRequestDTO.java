package io.github.raphaelmuniz.uniflow.dto.req.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AtividadeEntregaRequestDTO implements RequestData<AtividadeEntrega> {
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private String disciplinaId;
    private StatusEntregaEnum statusEntrega;
    private String estudanteDonoId;

    public AtividadeEntrega toModel() {
        return new AtividadeEntrega(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, statusEntrega, null, null);
    }
}
