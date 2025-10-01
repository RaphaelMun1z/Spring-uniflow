package io.github.raphaelmuniz.uniflow.services.regras.assinante;

import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.regras.assinante.config.ContextoValidacaoAssinatura;
import io.github.raphaelmuniz.uniflow.services.regras.assinante.config.RegraValidacaoAssinatura;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RegraAssinaturaUnicaAtiva implements RegraValidacaoAssinatura {
    private final AssinaturaUsuarioRepository repository;

    public RegraAssinaturaUnicaAtiva(AssinaturaUsuarioRepository repository) {
        this.repository = repository;
    }

    @Override
    public void verificar(ContextoValidacaoAssinatura contexto) {
        List<StatusAssinaturaUsuarioEnum> statusVigentes = StatusAssinaturaUsuarioEnum.getStatusVigentes();
        repository.findFirstVigenteByAssinanteId(
                contexto.assinante().getId(),
                statusVigentes,
                LocalDateTime.now()
        ).ifPresent(assinatura -> {
            throw new BusinessException("O usuário já possui uma assinatura ativa ou em teste.");
        });
    }
}
