package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeModelo extends Atividade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Boolean ativa = true;

    public AtividadeModelo(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade, Disciplina disciplina, Boolean ativa) {
        super(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, disciplina);
        this.ativa = ativa;
    }
}
