package io.github.raphaelmuniz.uniflow.dto.res.notificacao;

import io.github.raphaelmuniz.uniflow.entities.notificacao.Transmissao;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoDestinatarioEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoRemetenteEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TransmissaoResponseDTO {
    private String id;
    private String notificacaoId;
    private TipoRemetenteEnum remetenteTipo;
    private TipoDestinatarioEnum destinatarioTipo;
    private LocalDateTime dataDeLeitura;
    private String status;

    public TransmissaoResponseDTO(Transmissao data) {
        this.id = data.getId();
        this.notificacaoId = data.getNotificacao().getId();
        this.remetenteTipo = data.getRemetenteTipo();
        this.destinatarioTipo = data.getDestinatarioTipo();
        this.dataDeLeitura = data.getDataDeLeitura();
        this.status = data.getStatus();
    }

}
