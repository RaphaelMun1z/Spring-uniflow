package io.github.raphaelmuniz.uniflow.dto.res.notificacao;

import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NotificacaoResponseDTO {
    private final String id;
    private final String mensagem;
    private final LocalDateTime dataCriacao;
    private final boolean lida;
    private final String link;

    public NotificacaoResponseDTO(NotificacaoAssinante notificacaoAssinante) {
        this.id = notificacaoAssinante.getId();
        this.mensagem = notificacaoAssinante.getNotificacao().getMensagem();
        this.dataCriacao = notificacaoAssinante.getNotificacao().getDataCriacao();
        this.lida = notificacaoAssinante.isLida();
        this.link = notificacaoAssinante.getNotificacao().getLink();
    }
}