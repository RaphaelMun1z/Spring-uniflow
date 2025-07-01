package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import io.github.raphaelmuniz.plansFy.entities.TransmissaoDeNotificacao;
import io.github.raphaelmuniz.plansFy.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;

import java.time.LocalDateTime;
import java.util.List;

public class NotificacaoResponseDTO {
    private String id;
    private String titulo;
    private String descricao;
    private CategoriaNotificacaoEnum tipo;
    private String status;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeLeitura;
    private TipoRemetenteEnum remetenteTipo;
    private List<TransmissaoDeNotificacao> transmissoesDeNotificacao;

    public NotificacaoResponseDTO(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.titulo = notificacao.getTitulo();
        this.descricao = notificacao.getDescricao();
        this.tipo = notificacao.getTipo();
        this.status = notificacao.getStatus();
        this.dataDeCriacao = notificacao.getDataDeCriacao();
        this.dataDeLeitura = notificacao.getDataDeLeitura();
        this.remetenteTipo = notificacao.getRemetenteTipo();
        this.transmissoesDeNotificacao = notificacao.getTransmissoesDeNotificacao();
    }
}
