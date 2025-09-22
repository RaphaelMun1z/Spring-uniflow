package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.Notificacao;
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

    public NotificacaoResponseDTO(Notificacao notificacao) {
        this.id = notificacao.getId();
        this.titulo = notificacao.getTitulo();
        this.descricao = notificacao.getDescricao();
        this.tipo = notificacao.getTipo();
        this.status = notificacao.getStatus();
        this.dataDeCriacao = notificacao.getDataDeCriacao();
        this.dataDeLeitura = notificacao.getDataDeLeitura();
        this.remetenteTipo = notificacao.getRemetenteTipo();
    }
}
