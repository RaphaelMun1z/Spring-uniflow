package io.github.raphaelmuniz.uniflow.entities;

import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeGrupoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "atividade_grupo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "grupo_publicado_id"})
})
public class AtividadeGrupo extends Atividade implements Serializable {
    public AtividadeGrupo(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade, Disciplina disciplina, String id, VisivibilidadeAtividadeGrupoEnum visivibilidadeAtividade, Grupo grupoPublicado, Assinante criadorAtividade) {
        super(dataLancamento, prazoEntrega, titulo, descricao, dificuldade, disciplina);
        this.id = id;
        this.visivibilidadeAtividade = visivibilidadeAtividade;
        this.grupoPublicado = grupoPublicado;
        this.criadorAtividade = criadorAtividade;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Visivibilidade não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private VisivibilidadeAtividadeGrupoEnum visivibilidadeAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_publicado_id", nullable = false)
    private Grupo grupoPublicado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_atividade_id", nullable = false)
    private Assinante criadorAtividade;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "atividadeGrupoOrigem")
    private Set<AtividadeEstudante> copiasDosAssinantes = new HashSet<>();
}
