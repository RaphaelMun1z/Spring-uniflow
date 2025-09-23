package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Assinante extends Usuario implements Serializable {
    @NotNull(message = "Assinatura não pode ser nulo")
    @OneToOne(mappedBy = "assinante", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private AssinaturaUsuario assinaturaUsuario;

    @NotNull(message = "Atividades não pode ser nulo")
    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL)
    private List<AtividadeAssinante> atividadesAssinante = new ArrayList<>();

    @NotNull(message = "Grupos não pode ser nulo")
    @OneToMany(mappedBy = "inscrito", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> grupos = new HashSet<>();

    public Assinante(String id, String nome, String email, AssinaturaUsuario assinaturaUsuario, List<AtividadeCopia> atividades, Set<InscricaoGrupo> grupos) {
        super(id, nome, email);
        this.assinaturaUsuario = assinaturaUsuario;
        this.atividades = atividades;
        this.grupos = grupos;
    }
}
