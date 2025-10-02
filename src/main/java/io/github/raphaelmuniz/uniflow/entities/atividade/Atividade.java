package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@NoArgsConstructor
public abstract class Atividade implements Serializable {
    public Atividade(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade) {
        this.dataLancamento = dataLancamento;
        this.prazoEntrega = prazoEntrega;
        this.titulo = titulo;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
    }

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
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
}
