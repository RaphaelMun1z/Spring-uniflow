package io.github.raphaelmuniz.uniflow.dto.req.notificacao;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoRemetenteEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class NotificacaoRequestDTO implements RequestData<Notificacao> {
    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    private CategoriaNotificacaoEnum tipo;

    @NotBlank
    private String status;

    @NotNull
    private LocalDateTime dataDeCriacao;

    @NotNull
    private LocalDateTime dataDeLeitura;

    @NotNull
    private TipoRemetenteEnum remetenteTipo;

    public Notificacao toModel() {
        return new Notificacao(null, titulo, descricao, tipo, status, dataDeCriacao, remetenteTipo, null, null);
    }
}
