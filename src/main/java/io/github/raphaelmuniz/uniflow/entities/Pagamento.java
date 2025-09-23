package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagamento", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dataPagamento", "assinante"})
})
public class Pagamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Data de pagamento não pode ser nulo")
    private LocalDateTime dataPagamento;

    @NotNull(message = "Valor não pode ser nulo")
    private Double valor;

    @NotBlank(message = "Status não pode ser vazio/nulo")
    private String status;

    @NotBlank(message = "Metodo não pode ser vazio/nulo")
    private String metodo;

    @NotBlank(message = "Protocolo não pode ser vazio/nulo")
    private String protocolo;

    @NotNull(message = "Assinante Pagador não pode ser nulo")
    private Assinante assinantePagador;
}
