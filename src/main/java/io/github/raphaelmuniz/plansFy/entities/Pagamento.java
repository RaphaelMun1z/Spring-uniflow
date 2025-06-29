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

    @NotNull
    private LocalDateTime dataPagamento;

    @NotNull
    private Double valor;

    @NotNull
    private String status;

    @NotNull
    private String metodo;

    @NotBlank
    private String protocolo;

    @NotNull
    private Assinante assinante;
}
