package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.AtividadeModelo;
import io.github.raphaelmuniz.plansFy.entities.Disciplina;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.servlet.tags.ArgumentTag;

import java.time.LocalDateTime;

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
