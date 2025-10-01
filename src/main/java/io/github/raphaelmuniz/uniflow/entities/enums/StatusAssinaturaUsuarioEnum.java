package io.github.raphaelmuniz.uniflow.entities.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StatusAssinaturaUsuarioEnum {
    ATIVA(true),
    INADIMPLENTE(false),
    CANCELADA(false),
    EM_TESTE(true),
    PENDENTE_PAGAMENTO(false);

    private final boolean isVigente;

    StatusAssinaturaUsuarioEnum(boolean isVigente) {
        this.isVigente = isVigente;
    }

    public static List<StatusAssinaturaUsuarioEnum> getStatusVigentes() {
        return Arrays.stream(StatusAssinaturaUsuarioEnum.values())
                .filter(status -> status.isVigente)
                .collect(Collectors.toList());
    }
}
