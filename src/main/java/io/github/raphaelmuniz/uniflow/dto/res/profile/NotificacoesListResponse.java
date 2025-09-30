package io.github.raphaelmuniz.uniflow.dto.res.profile;

import io.github.raphaelmuniz.uniflow.dto.res.NotificacaoResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NotificacoesListResponse {
    private List<NotificacaoResponseDTO> notificacoes;
    private long totalElementos;
}
