package io.github.raphaelmuniz.uniflow.dto.req;

import io.github.raphaelmuniz.uniflow.entities.Transmissao;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoDestinatarioEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoRemetenteEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransmissaoRequestDTO {
    @NotNull(message = "Tipo de remetente não pode ser nulo")
    private TipoRemetenteEnum remetenteTipo;

    @NotNull(message = "Tipo de destinatário não pode ser nulo")
    private TipoDestinatarioEnum destinatarioTipo;

    @NotEmpty(message = "A lista de notificações não pode ser vazia")
    private List<String> notificacoesId;

    public Transmissao toModel() {
        return new Transmissao(null, null, remetenteTipo, destinatarioTipo, null, null);
    }
}
