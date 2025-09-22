package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.NotificacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Notificacao;
import io.github.raphaelmuniz.uniflow.repositories.NotificacaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService extends GenericCrudServiceImpl<NotificacaoRequestDTO, NotificacaoResponseDTO, Notificacao, String> {
    protected NotificacaoService(NotificacaoRepository repository) {
        super(repository, NotificacaoRequestDTO::toModel, NotificacaoResponseDTO::new);
    }
}
