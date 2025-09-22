package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.AtividadeModelo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeModeloResponseDTO extends AtividadeResponseDTO {
    private boolean ativa;

    public AtividadeModeloResponseDTO(AtividadeModelo atv) {
        super(atv.getId(), atv.getDataLancamento(), atv.getPrazoEntrega(), atv.getTitulo(), atv.getDescricao(), atv.getDificuldade(), atv.getDisciplina().getId());
        this.ativa = atv.getAtiva();
    }
}
