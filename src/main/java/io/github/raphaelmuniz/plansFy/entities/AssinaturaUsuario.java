package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class AssinaturaUsuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull(message = "Assinatura não pode ser nulo")
    private AssinaturaModelo assinatura;

    @NotNull(message = "Data início não pode ser nulo")
    private LocalDateTime dataInicio;

    @NotNull(message = "Data expiração não pode ser nulo")
    private LocalDateTime dataExpiracao;

    @NotNull(message = "Status não pode ser nulo")
    private Boolean status;
}
