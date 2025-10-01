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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Grupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private String id;

    @NotBlank(message = "Título não pode ser vazio/nulo")
    private String titulo;

    @NotBlank(message = "Descrição não pode ser vazio/nulo")
    private String descricao;

    @NotNull(message = "Tipo de Grupo não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private TipoGrupoEnum tipoGrupo;

    @NotNull(message = "Status do Grupo não pode ser nulo")
    @Enumerated(EnumType.STRING)
    private StatusGrupoEnum statusGrupo;

    @NotNull(message = "Código Convite não pode ser nulo")
    @Column(unique = true)
    private String codigoConvite;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_pai_id")
    private Grupo grupoPai;

    @ToString.Exclude
    @OneToMany(mappedBy = "grupoPai", cascade = CascadeType.ALL)
    private List<Grupo> subGrupos = new ArrayList<>();

    @ToString.Exclude
    @NotNull(message = "Criador do grupo não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assinante_criador_id", nullable = false)
    private Assinante assinanteCriadorGrupo;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull(message = "Inscrições não pode ser nulo")
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> inscricoes = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull(message = "Atividades Publicadas não pode ser nulo")
    @OneToMany(mappedBy = "grupoPublicado", cascade = CascadeType.ALL)
    private Set<AtividadeAvaliativa> atividadesPublicadas = new HashSet<>();

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
