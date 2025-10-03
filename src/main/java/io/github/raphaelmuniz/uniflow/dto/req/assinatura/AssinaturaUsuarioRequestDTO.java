package io.github.raphaelmuniz.uniflow.dto.req.assinatura;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AssinaturaUsuarioRequestDTO {
    @NotBlank(message = "O ID do usuário (assinante) é obrigatório.")
    private String usuarioId;

    @NotBlank(message = "O ID do modelo de assinatura é obrigatório.")
    private String modeloId;
}