package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import io.github.raphaelmuniz.plansFy.entities.TransmissaoDeNotificacao;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoDestinatarioEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
public class TransmissaoDeNotificacaoResponseDTO {
    private String id;
    private TipoRemetenteEnum remetenteTipo;
    private String remetenteId;
    private TipoDestinatarioEnum destinatarioTipo;
    private String destinatarioId;
    private List<Notificacao> notificacoes;

    public TransmissaoDeNotificacaoResponseDTO(TransmissaoDeNotificacao data) {
        this.id = data.getId();
        this.remetenteTipo = data.getRemetenteTipo();
        this.remetenteId = data.getRemetenteId();
        this.destinatarioTipo = data.getDestinatarioTipo();
        this.destinatarioId = data.getDestinatarioId();
        this.notificacoes = data.getNotificacoes();
    }

}
