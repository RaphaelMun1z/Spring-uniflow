package io.github.raphaelmuniz.uniflow.entities.grupo;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "grupo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"titulo", "disciplina_id"})
})
public class Grupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O título não pode ser vazio ou nulo.")
    @Column(nullable = false)
    private String titulo;

    @NotBlank(message = "A descrição não pode ser vazia ou nula.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @NotNull(message = "O tipo de Grupo não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoGrupoEnum tipoGrupo;

    @NotNull(message = "O status do Grupo não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusGrupoEnum statusGrupo;

    @Column(unique = true)
    private String codigoConvite;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_pai_id")
    private Grupo grupoPai;

    @ToString.Exclude
    @OneToMany(mappedBy = "grupoPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grupo> subGrupos = new ArrayList<>();

    @ToString.Exclude
    @NotNull(message = "O criador do grupo não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_criador_id", nullable = false)
    private Assinante assinanteCriadorGrupo;

    @ToString.Exclude
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<InscricaoGrupo> inscricoes = new HashSet<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "grupoPublicado")
    private Set<AtividadeAvaliativa> atividadesPublicadas = new HashSet<>();

    @ToString.Exclude
    @NotNull(message = "A disciplina não pode ser nula.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disciplina_id", nullable = false)
    private Disciplina disciplina;

    public void addInscricao(InscricaoGrupo inscricao) {
        this.inscricoes.add(inscricao);
        inscricao.setGrupo(this);
    }

    public void removeInscricao(InscricaoGrupo inscricao) {
        this.inscricoes.remove(inscricao);
        inscricao.setGrupo(null);
    }

    public void addAtividadePublicada(AtividadeAvaliativa atividade) {
        this.atividadesPublicadas.add(atividade);
        atividade.setGrupoPublicado(this);
    }

    public void removeAtividadePublicada(AtividadeAvaliativa atividade) {
        this.atividadesPublicadas.remove(atividade);
        atividade.setGrupoPublicado(null);
    }

    public static String gerarCodigoConvite() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
