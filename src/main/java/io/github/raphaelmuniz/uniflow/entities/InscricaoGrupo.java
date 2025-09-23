package io.github.raphaelmuniz.uniflow.entities;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inscricao_grupo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"grupo_id", "inscrito_id"})
})
public class InscricaoGrupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Data entrada não pode ser nulo")
    private LocalDateTime dataEntrada;

    @NotBlank(message = "Papel no grupo não pode ser vazio/nulo")
    private PapelGrupoEnum papelNoGrupo;

    @NotNull(message = "Grupo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @NotNull(message = "Membro não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "membro_id")
    private Assinante membro;

}
