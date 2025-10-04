package io.github.raphaelmuniz.uniflow.dto.res.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusPlanoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AssinaturaModeloResponseDTO(
        String id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer duracaoEmMeses,
        Integer limiteDeGrupos,
        Integer limiteDeSubGrupos,
        Integer limiteMembrosPorGrupo,
        Boolean permiteAnalytics,
        Boolean permiteTemplatesDeAtividade,
        Boolean permiteCriarSubgrupos,
        StatusPlanoEnum status,
        LocalDateTime dataCriacao,
        LocalDateTime dataAtualizacao
) {
    public AssinaturaModeloResponseDTO(AssinaturaModelo assinaturaModelo) {
        this(
                assinaturaModelo.getId(),
                assinaturaModelo.getNome(),
                assinaturaModelo.getDescricao(),
                assinaturaModelo.getPreco(),
                assinaturaModelo.getDuracaoEmMeses(),
                assinaturaModelo.getLimiteDeGrupos(),
                assinaturaModelo.getLimiteDeSubGrupos(),
                assinaturaModelo.getLimiteMembrosPorGrupo(),
                assinaturaModelo.getPermiteAnalytics(),
                assinaturaModelo.getPermiteTemplatesDeAtividade(),
                assinaturaModelo.getPermiteCriarSubgrupos(),
                assinaturaModelo.getStatus(),
                assinaturaModelo.getDataCriacao(),
                assinaturaModelo.getDataAtualizacao()
        );
    }
}