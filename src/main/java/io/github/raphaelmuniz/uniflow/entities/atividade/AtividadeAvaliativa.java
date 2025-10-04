package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
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
@EqualsAndHashCode(of = "id", callSuper = false)
@Table(name = "atividade_avaliativa", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "grupo_publicado_id"})
})
public class AtividadeAvaliativa extends Atividade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "A nota máxima não pode ser nula.")
    @Positive(message = "A nota máxima deve ser um valor positivo.")
    @Column(nullable = false)
    private Double notaMaxima;

    @NotNull(message = "O campo 'permiteEnvioAtrasado' não pode ser nulo.")
    @Column(nullable = false)
    private Boolean permiteEnvioAtrasado;

    @NotNull(message = "A visibilidade não pode ser nula.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VisivibilidadeAtividadeEnum visivibilidadeAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_publicado_id", nullable = false)
    @ToString.Exclude
    private Grupo grupoPublicado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_atividade_id", nullable = false)
    @ToString.Exclude
    private Assinante assinanteCriadorAtividade;

    @OneToMany(mappedBy = "atividadeAvaliativaOrigem", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<AtividadeEntrega> copiasDosAssinantes = new HashSet<>();
}