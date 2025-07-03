package io.github.raphaelmuniz.plansFy.dto.req;

import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import io.github.raphaelmuniz.plansFy.entities.TransmissaoDeNotificacao;
import io.github.raphaelmuniz.plansFy.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.TipoRemetenteEnum;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class NotificacaoRequestDTO {
    private String titulo;
    private String descricao;
    private CategoriaNotificacaoEnum tipo;
    private String status;
    private LocalDateTime dataDeCriacao;
    private LocalDateTime dataDeLeitura;
    private TipoRemetenteEnum remetenteTipo;
    private List<String> transmissoesDeNotificacaoId;

    public Notificacao toModel() {
        return new Notificacao(null, titulo, descricao, tipo, status, dataDeCriacao, dataDeLeitura, remetenteTipo, null);
    }
}
