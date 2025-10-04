package io.github.raphaelmuniz.uniflow.entities.autorizacao;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "permissao")
public class Permissao implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "O nome da permissão é obrigatório.")
    @Column(nullable = false, unique = true)
    private String nome;

    @Override
    public String getAuthority() {
        return this.nome;
    }
}