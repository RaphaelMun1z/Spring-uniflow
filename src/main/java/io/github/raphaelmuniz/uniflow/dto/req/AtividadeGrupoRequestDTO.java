package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.AtividadeGrupo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeGrupoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AtividadeGrupoRequestDTO {
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private VisivibilidadeAtividadeGrupoEnum visivibilidade;
    private String grupoId;
    private String criadorId;
    private String disciplinaId;

    public AtividadeGrupo toModel() {
        return new AtividadeGrupo(null, visivibilidade, null, null);
    }
}
