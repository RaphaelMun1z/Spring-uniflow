package io.github.raphaelmuniz.uniflow.entities;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inscricao_grupo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"grupo_id", "estudante_membro_id"})
})
public class InscricaoGrupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Data entrada não pode ser nulo")
    private LocalDateTime dataEntrada;

    @NotNull(message = "Papel no grupo não pode ser vazio/nulo")
    private PapelGrupoEnum papelNoGrupo;

    @NotNull(message = "Grupo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @NotNull(message = "Estudante membro não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "estudante_membro_id")
    private Estudante estudanteMembro;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InscricaoGrupo that = (InscricaoGrupo) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
