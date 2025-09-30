package io.github.raphaelmuniz.uniflow.dto.res.atividade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoAtividadeResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private Double nota;
    private String feedback;
}
