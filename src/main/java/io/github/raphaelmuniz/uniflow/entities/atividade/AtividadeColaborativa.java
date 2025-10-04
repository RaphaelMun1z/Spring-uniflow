package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "atividade_colaborativa", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "grupo_id"})
})
public class AtividadeColaborativa extends Atividade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id", nullable = false)
    @ToString.Exclude
    private Grupo grupo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id", nullable = false)
    @ToString.Exclude
    private Assinante criador;

    @OneToMany(mappedBy = "atividadeColaborativa", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<TarefaStatusMembro> statusMembros = new ArrayList<>();
}
