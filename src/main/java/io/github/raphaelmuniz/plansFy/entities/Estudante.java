package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Estudante extends Assinante implements Serializable {
    @NotNull(message = "Período não pode ser nulo")
    private Integer periodo;

    public Estudante(String id, String nome, String email, AssinaturaUsuario assinatura, List<AtividadeCopia> atividades, Set<InscricaoGrupo> grupos, Integer periodo) {
        super(id, nome, email, assinatura, atividades, grupos);
        this.periodo = periodo;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Estudante: {");
        sb.append(" Estudante_periodo=").append(periodo).append('\n');
        sb.append(", Assinante_assinatura_id=").append(getAssinaturaUsuario().getId()).append('\n');
        sb.append(", Assinante_atividades_size=").append(getAtividades().size()).append('\n');
        sb.append(", Assinante_grupos_size=").append(getGrupos().size()).append('\n');
        sb.append(", Usuario_id='").append(getId()).append('\n');
        sb.append(", Usuario_nome='").append(getNome()).append('\n');
        sb.append(", Usuario_email='").append(getEmail()).append('\n');
        sb.append('}');
        return sb.toString();
    }
}
