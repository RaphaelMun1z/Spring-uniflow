package io.github.raphaelmuniz.plansFy.entities;

import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtividadeCopia extends Atividade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private StatusEntregaEnum status;

    @ManyToOne
    @JoinColumn(name = "grupo_id")
    private Grupo grupo;

    @ManyToOne
    @JoinColumn(name = "assinante_id")
    private Assinante assinante;

}
