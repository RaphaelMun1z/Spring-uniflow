package io.github.raphaelmuniz.uniflow.entities.atividade;

import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.VisivibilidadeAtividadeEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
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
public class AtividadeAvaliativa extends Atividade implements Serializable {
    public AtividadeAvaliativa(LocalDateTime dataLancamento, LocalDateTime prazoEntrega, String titulo, String descricao, DificuldadeEnum dificuldade, String id, VisivibilidadeAtividadeEnum visivibilidadeAtividade, Grupo grupoPublicado, Assinante criadorAtividade) {
        super(dataLancamento, prazoEntrega, titulo, descricao, dificuldade);
        this.id = id;
        this.visivibilidadeAtividade = visivibilidadeAtividade;
        this.grupoPublicado = grupoPublicado;
        this.assinanteCriadorAtividade = criadorAtividade;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Nota Máxima não pode ser nulo")
    private Double notaMaxima;

    @NotNull(message = "Permite Envio Atrasado não pode ser nulo")
    private Boolean permiteEnvioAtrasado;

    @NotNull(message = "Visivibilidade não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private VisivibilidadeAtividadeEnum visivibilidadeAtividade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_publicado_id", nullable = false)
    private Grupo grupoPublicado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_atividade_id", nullable = false)
    private Assinante assinanteCriadorAtividade;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "atividadeAvaliativaOrigem")
    private Set<AtividadeEntrega> copiasDosAssinantes = new HashSet<>();
}
