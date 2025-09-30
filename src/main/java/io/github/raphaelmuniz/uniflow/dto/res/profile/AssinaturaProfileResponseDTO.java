package io.github.raphaelmuniz.uniflow.dto.res.profile;

import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinaturaProfileResponseDTO {
    private String assinaturaId;
    private LocalDateTime dataInicio;
    private LocalDateTime dataExpiracao;
    private Boolean status;
    private AssinaturaModeloResponseDTO assinaturaModelo;
}
