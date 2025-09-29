package io.github.raphaelmuniz.uniflow.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeAvaliacaoResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private Double nota;
    private String feedback;
}
