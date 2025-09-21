package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.Transmissao;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoDestinatarioEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;
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
