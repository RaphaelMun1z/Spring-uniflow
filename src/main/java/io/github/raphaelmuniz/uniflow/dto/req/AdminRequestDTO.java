package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRequestDTO {
    private String nome;
    private String email;
    private String senha;

    public Admin toModel(){
        return new Admin(null, nome, email, senha);
    }
}
