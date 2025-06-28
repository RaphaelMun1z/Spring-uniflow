package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Professor extends Assinante implements Serializable {
    private String areaAtuacao;

    public Professor(String id, String nome, String email, AssinaturaUsuario assinatura, List<AtividadeCopia> atividades, Set<InscricaoGrupo> grupos, String areaAtuacao) {
        super(id, nome, email, assinatura, atividades, grupos);
        this.areaAtuacao = areaAtuacao;
    }
}