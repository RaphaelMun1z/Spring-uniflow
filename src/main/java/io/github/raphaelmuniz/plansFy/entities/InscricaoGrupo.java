package io.github.raphaelmuniz.plansFy.entities;

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

    @NotNull(message = "Grupo não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @NotNull(message = "Inscrito não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "inscrito_id")
    private Assinante inscrito;

    @NotNull(message = "Data entrada não pode ser nulo")
    private LocalDateTime dataEntrada;

    @NotBlank(message = "Papel no grupo não pode ser vazio/nulo")
    private String papelNoGrupo;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
