package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.atividade.TarefaStatusMembro;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"atividadesEstudante", "tarefasEstudante"})
@Table(name = "estudante")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Estudante extends Assinante implements Serializable {
    @NotNull(message = "O período de ingresso não pode ser nulo.")
    @Embedded
    private PeriodoLetivo periodoDeIngresso;

    @OneToMany(mappedBy = "estudanteDono", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AtividadeEntrega> atividadesEstudante = new HashSet<>();

    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TarefaStatusMembro> tarefasEstudante = new HashSet<>();

    @Transient
    public int getPeriodoAtual() {
        if (this.periodoDeIngresso == null || this.periodoDeIngresso.ano() == null || this.periodoDeIngresso.semestre() == null) {
            return 0;
        }

        LocalDate hoje = LocalDate.now();
        int anoAtual = hoje.getYear();
        int semestreAtual = (hoje.getMonthValue() < 7) ? 1 : 2;

        int anosDeDiferenca = anoAtual - this.periodoDeIngresso.ano();
        int semestresDeDiferenca = semestreAtual - this.periodoDeIngresso.semestre();

        int totalSemestres = (anosDeDiferenca * 2) + semestresDeDiferenca + 1;

        return Math.max(1, totalSemestres);
    }
}