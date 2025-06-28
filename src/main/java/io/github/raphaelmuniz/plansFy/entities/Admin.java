package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class Admin extends Usuario implements Serializable {
    public Admin(String id, String nome, String email) {
        super(id, nome, email);
    }
}
