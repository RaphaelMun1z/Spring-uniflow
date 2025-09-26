package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Título não pode ser vazio/nulo")
    private String titulo;

    @NotBlank(message = "Descrição não pode ser vazio/nulo")
    private String descricao;

    @NotNull(message = "Atividades Publicadas não pode ser nulo")
    @OneToMany(mappedBy = "grupoPublicado", cascade = CascadeType.ALL)
    private Set<AtividadeGrupo> atividadesPublicadas = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull(message = "Inscrições não pode ser nulo")
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> inscricoes = new HashSet<>();

    @NotNull(message = "Criador do grupo não pode ser nulo")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id", nullable = false)
    private Assinante criador;

    public void addInscricao(InscricaoGrupo inscricao) {
        this.inscricoes.add(inscricao);
        inscricao.setGrupo(this);
    }

    public void removeInscricao(InscricaoGrupo inscricao) {
        this.inscricoes.remove(inscricao);
        inscricao.setGrupo(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupo grupo = (Grupo) o;
        return id != null && id.equals(grupo.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
