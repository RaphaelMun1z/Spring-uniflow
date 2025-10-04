package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "admin")
public class Admin extends Usuario implements Serializable {
    public Admin(String nome, String email, String senha, Papel papel) {
        super(nome, email, senha, papel);
    }
}