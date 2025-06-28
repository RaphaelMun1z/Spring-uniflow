package io.github.raphaelmuniz.plansFy.entities;

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
    @NotNull
    private AssinaturaUsuario assinatura;

    @NotNull
    @OneToMany(mappedBy = "assinante", cascade = CascadeType.ALL)
    private List<AtividadeCopia> atividades = new ArrayList<>();

    @NotNull
    @OneToMany(mappedBy = "inscrito", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> grupos = new HashSet<>();

    public Assinante(String id, String nome, String email, AssinaturaUsuario assinatura, List<AtividadeCopia> atividades, Set<InscricaoGrupo> grupos) {
        super(id, nome, email);
        this.assinatura = assinatura;
        this.atividades = atividades;
        this.grupos = grupos;
    }
}
