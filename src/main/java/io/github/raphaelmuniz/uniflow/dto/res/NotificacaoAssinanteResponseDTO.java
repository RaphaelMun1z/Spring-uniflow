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
public class NotificacaoAssinanteResponseDTO {
    private String id;
    private Boolean lido;
    private LocalDateTime dataLeitura;

    public NotificacaoAssinanteResponseDTO(NotificacaoAssinante notificacaoAssinante) {
        this.id = notificacaoAssinante.getId();
        this.lido = notificacaoAssinante.getFoiLido();
        this.dataLeitura = notificacaoAssinante.getDataLeitura();
    }
}
