package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.*;

import java.io.Serializable;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "permissao")
@Getter
@Setter
@EqualsAndHashCode
public class Permissao implements GrantedAuthority, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String descricao;

    @Override
    public String getAuthority() {
        return this.descricao;
    }

}
