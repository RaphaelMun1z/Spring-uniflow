package io.github.raphaelmuniz.uniflow.entities.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"grupo", "membro"})
@EqualsAndHashCode(of = "id")
@Table(name = "inscricao_grupo", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"grupo_id", "membro_id"})
})
public class InscricaoGrupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataEntrada;

    @NotNull(message = "O papel no grupo não pode ser nulo.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PapelGrupoEnum papelNoGrupo;

    @NotNull(message = "O grupo não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @NotNull(message = "O membro não pode ser nulo.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membro_id", nullable = false)
    private Assinante membro;
}