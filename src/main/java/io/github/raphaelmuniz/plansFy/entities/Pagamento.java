package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @NotNull(message = "Assinante não pode ser nulo")
    private Assinante assinante;
}
