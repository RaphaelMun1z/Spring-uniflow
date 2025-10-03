package io.github.raphaelmuniz.uniflow.dto.req.autorizacao;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EsquecerSenhaRequestDTO(
        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O formato do email é inválido.")
        String email
) {}