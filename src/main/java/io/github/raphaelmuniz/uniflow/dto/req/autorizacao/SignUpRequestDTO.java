package io.github.raphaelmuniz.uniflow.dto.req.autorizacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequestDTO {

    public enum TipoUsuario {
        ESTUDANTE,
        PROFESSOR
    }

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @Email(message = "O email fornecido é inválido.")
    @NotBlank(message = "O email é obrigatório.")
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    private String senha;

    @NotNull(message = "O tipo de usuário (ESTUDANTE ou PROFESSOR) é obrigatório.")
    private TipoUsuario tipo;

    private Integer periodo;
    private String areaAtuacao;
}