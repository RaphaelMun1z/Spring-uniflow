package io.github.raphaelmuniz.uniflow.entities.grupo;

import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "disciplina", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "periodo", "ano", "semestre"})
})
public class Disciplina implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O nome não pode ser vazio ou nulo.")
    @Column(nullable = false)
    private String nome;

    @NotNull(message = "O período não pode ser nulo.")
    @Positive(message = "O período deve ser um número positivo.")
    @Column(nullable = false)
    private Integer periodo;

    @NotNull(message = "A dificuldade não pode ser nula.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DificuldadeEnum dificuldade;

    @NotNull(message = "O período letivo не pode ser nulo.")
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "ano", column = @Column(name = "ano", nullable = false)),
            @AttributeOverride(name = "semestre", column = @Column(name = "semestre", nullable = false))
    })
    private PeriodoLetivo periodoLetivo;

    @ToString.Exclude
    @OneToMany(mappedBy = "disciplina")
    private Set<Grupo> grupos = new HashSet<>();

    public void addGrupo(Grupo grupo) {
        this.grupos.add(grupo);
        grupo.setDisciplina(this);
    }

    public void removeGrupo(Grupo grupo) {
        this.grupos.remove(grupo);
        grupo.setDisciplina(null);
    }
}