package io.github.raphaelmuniz.uniflow.dto.res.profile;

import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificacoesProfileResponse {
    private List<NotificacaoResponseDTO> notificacoes;
    private long totalElementos;
}
