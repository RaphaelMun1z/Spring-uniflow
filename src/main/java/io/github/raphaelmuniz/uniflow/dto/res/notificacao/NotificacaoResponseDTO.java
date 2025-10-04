package io.github.raphaelmuniz.uniflow.dto.res.notificacao;

import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;

import java.time.LocalDateTime;

public record NotificacaoResponseDTO(
        String id,
        String mensagem,
        LocalDateTime dataCriacao,
        boolean lida,
        String link
) {
    public static NotificacaoResponseDTO fromEntity(NotificacaoAssinante notificacaoAssinante) {
        if (notificacaoAssinante == null || notificacaoAssinante.getNotificacao() == null) {
            return null;
        }
        return new NotificacaoResponseDTO(
                notificacaoAssinante.getId(),
                notificacaoAssinante.getNotificacao().getMensagem(),
                notificacaoAssinante.getNotificacao().getDataCriacao(),
                notificacaoAssinante.isLida(),
                notificacaoAssinante.getNotificacao().getLink()
        );
    }
}