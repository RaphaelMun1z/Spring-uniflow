package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AtividadeGrupo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeGrupoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeGrupoResponseDTO {
    private String id;
    private LocalDateTime dataLancamento;
    private LocalDateTime prazoEntrega;
    private String titulo;
    private String descricao;
    private DificuldadeEnum dificuldade;
    private VisivibilidadeAtividadeGrupoEnum visivibilidade;
    private String grupoId;
    private String criadorId;

    public AtividadeGrupoResponseDTO(AtividadeGrupo atividadeGrupo) {
        this.id = atividadeGrupo.getId();
        this.dataLancamento = atividadeGrupo.getDataLancamento();
        this.prazoEntrega = atividadeGrupo.getPrazoEntrega();
        this.titulo = atividadeGrupo.getTitulo();
        this.descricao = atividadeGrupo.getDescricao();
        this.dificuldade = atividadeGrupo.getDificuldade();
        this.visivibilidade = atividadeGrupo.getVisivibilidadeAtividade();
        this.grupoId = atividadeGrupo.getGrupoPublicado().getId();
        this.criadorId = atividadeGrupo.getCriadorAtividade().getId();
    }

    public AtividadeGrupo toModel() {
        return new AtividadeGrupo(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, null, id, visivibilidade, null, null);
    }
}
