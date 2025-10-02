package io.github.raphaelmuniz.uniflow.entities.grupo;

import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "disciplina", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nome", "periodo", "ano", "semestre"})
})
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

    @ToString.Exclude
    @OneToMany(mappedBy = "disciplina", cascade = CascadeType.ALL, orphanRemoval = true)
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
