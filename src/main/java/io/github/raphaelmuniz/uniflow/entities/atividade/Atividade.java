package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Atividade implements Serializable {
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataLancamento;

    @NotNull(message = "O prazo de entrega não pode ser nulo.")
    @Future(message = "O prazo de entrega deve ser uma data no futuro.")
    @Column(nullable = false)
    private LocalDateTime prazoEntrega;

    @NotBlank(message = "O título não pode ser vazio ou nulo.")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "A descrição não pode ser vazia ou nula.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "A dificuldade não pode ser nula.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificuldadeEnum dificuldade;
}
