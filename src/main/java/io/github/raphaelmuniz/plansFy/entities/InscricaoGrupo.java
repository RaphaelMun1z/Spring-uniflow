package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscricaoGrupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "inscrito_id")
    private Assinante inscrito;

    @NotNull
    private LocalDateTime dataEntrada;

    @NotNull
    private String papelNoGrupo;
}
