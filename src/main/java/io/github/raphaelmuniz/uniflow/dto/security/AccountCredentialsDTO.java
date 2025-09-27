package io.github.raphaelmuniz.uniflow.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountCredentialsDTO implements Serializable {
    @NotBlank(message = "O email não pode ser vazio")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "A senha não pode ser vazia")
    private String password;
}
