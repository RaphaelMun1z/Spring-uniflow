package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Estudante extends Assinante implements Serializable {
    private Integer periodo;

    public Estudante(String id, String nome, String email, AssinaturaUsuario assinatura, List<AtividadeCopia> atividades, Set<InscricaoGrupo> grupos, Integer periodo) {
        super(id, nome, email, assinatura, atividades, grupos);
        this.periodo = periodo;
    }
}
