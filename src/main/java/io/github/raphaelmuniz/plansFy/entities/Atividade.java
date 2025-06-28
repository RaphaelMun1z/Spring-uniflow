package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Atividade implements Serializable {
    @NotNull
    private LocalDateTime dataLancamento;

    @NotNull
    private LocalDateTime prazoEntrega;

    @NotNull
    private String titulo;

    @NotNull
    private String descricao;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DificuldadeEnum dificuldade;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;
}
