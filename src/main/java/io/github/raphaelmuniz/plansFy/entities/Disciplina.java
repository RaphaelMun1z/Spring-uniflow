package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Disciplina implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Nome não pode ser vazio/nulo")
    private String nome;

    @NotNull(message = "Período não pode ser nulo")
    private Integer periodo;

    @NotNull(message = "Dificuldade não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private DificuldadeEnum dificuldade;

    @NotNull(message = "Período letivo não pode ser nulo")
    @Embedded
    private PeriodoLetivo periodoLetivo;
}
