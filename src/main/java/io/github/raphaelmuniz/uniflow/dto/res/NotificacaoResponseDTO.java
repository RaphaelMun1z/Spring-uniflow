package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoRemetenteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificacaoResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private CategoriaNotificacaoEnum tipo;
    private String status;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeLeitura;
    private TipoRemetenteEnum remetenteTipo;
    private Boolean lido;
    private LocalDateTime dataLeitura;

    public NotificacaoResponseDTO(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.titulo = notificacao.getTitulo();
        this.descricao = notificacao.getDescricao();
        this.tipo = notificacao.getTipo();
        this.status = notificacao.getStatus();
        this.dataDeCriacao = notificacao.getDataDeCriacao();
        this.remetenteTipo = notificacao.getRemetenteTipo();
    }

    public NotificacaoResponseDTO(NotificacaoAssinante notificacaoAssinante) {
        this.id = notificacaoAssinante.getId();
        this.titulo = notificacaoAssinante.getNotificacao().getTitulo();
        this.descricao = notificacaoAssinante.getNotificacao().getDescricao();
        this.tipo = notificacaoAssinante.getNotificacao().getTipo();
        this.status = notificacaoAssinante.getNotificacao().getStatus();
        this.dataDeCriacao = notificacaoAssinante.getNotificacao().getDataDeCriacao();
        this.remetenteTipo = notificacaoAssinante.getNotificacao().getRemetenteTipo();
        this.lido = notificacaoAssinante.getFoiLido();
        this.dataLeitura = notificacaoAssinante.getDataLeitura();
    }
}
