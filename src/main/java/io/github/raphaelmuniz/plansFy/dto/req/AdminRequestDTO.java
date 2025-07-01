package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRequestDTO {
    private String nome;
    private String email;

    public Admin toModel(){
        return new Admin(null, nome, email);
    }
}
