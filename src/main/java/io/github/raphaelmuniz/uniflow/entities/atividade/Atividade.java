package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class Atividade implements Serializable {
    public Atividade(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade, Disciplina disciplina) {
        this.dataLancamento = dataLancamento;
        this.prazoEntrega = prazoEntrega;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.disciplina = disciplina;
    }

    @NotNull(message = "Data lançamento não pode ser nulo")
    private LocalDateTime dataLancamento;

    @NotNull(message = "Prazo entrega não pode ser nulo")
    private LocalDateTime prazoEntrega;

    @NotNull(message = "Título não pode ser nulo")
    private String titulo;

    @NotNull(message = "Descrição não pode ser nulo")
    private String descricao;

    @NotNull(message = "Dificuldade não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private DificuldadeEnum dificuldade;

    @NotNull(message = "Disciplina não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;
}
