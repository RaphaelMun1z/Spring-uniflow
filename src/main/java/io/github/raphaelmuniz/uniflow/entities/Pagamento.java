package io.github.raphaelmuniz.uniflow.entities;

import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@EqualsAndHashCode(exclude = "assinantePagador")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pagamento", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"data_pagamento", "assinante_pagador_id"})
})
public class Pagamento implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Data de pagamento não pode ser nulo")
    private LocalDateTime dataPagamento;

    @NotNull(message = "Valor não pode ser nulo")
    private BigDecimal valor;

    @NotBlank(message = "Status não pode ser vazio/nulo")
    private String status;

    @NotBlank(message = "Metodo não pode ser vazio/nulo")
    private String metodo;

    @NotBlank(message = "Protocolo não pode ser vazio/nulo")
    private String protocolo;

    @NotNull(message = "Assinante Pagador não pode ser nulo")
    @ManyToOne
    @JoinColumn(name = "assinante_pagador_id")
    private Assinante assinantePagador;


}
