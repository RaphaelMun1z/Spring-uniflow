package io.github.raphaelmuniz.uniflow.dto.req.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.usuario.Admin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminRequestDTO implements RequestData<Admin> {
    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    @NotBlank
    private String senha;

    public Admin toModel(){
        return new Admin(nome, email, senha, null);
    }
}
