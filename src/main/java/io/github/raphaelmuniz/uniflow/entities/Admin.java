package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Admin extends Usuario implements Serializable {
    public Admin(String nome, String email, String senha, Papel papel) {
        super(nome, email, senha, papel);
    }
}
