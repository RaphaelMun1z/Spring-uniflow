package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import io.github.raphaelmuniz.plansFy.entities.TransmissaoDeNotificacao;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoDestinatarioEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransmissaoDeNotificacaoRequestDTO {
    private TipoRemetenteEnum remetenteTipo;
    private String remetenteId;
    private TipoDestinatarioEnum destinatarioTipo;
    private String destinatarioId;
    private List<String> notificacoesId;

    public TransmissaoDeNotificacao toModel() {
        return new TransmissaoDeNotificacao(null, remetenteTipo, remetenteId, destinatarioTipo, destinatarioId, null);
    }
}
