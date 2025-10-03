package io.github.raphaelmuniz.uniflow.dto.req.notificacao;


import io.github.raphaelmuniz.uniflow.entities.enums.CategoriaNotificacaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificacaoBroadcastRequestDTO(
        @NotBlank(message = "A mensagem da notificação é obrigatória.")
        String mensagem,

        @NotNull(message = "A categoria da notificação é obrigatória.")
        CategoriaNotificacaoEnum categoria,

        String link
) {}