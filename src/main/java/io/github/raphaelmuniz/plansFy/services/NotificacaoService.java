package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.NotificacaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.NotificacaoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import io.github.raphaelmuniz.plansFy.repositories.NotificacaoRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoService extends GenericCrudServiceImpl<NotificacaoRequestDTO, NotificacaoResponseDTO, Notificacao, String> {
    protected NotificacaoService(NotificacaoRepository repository) {
        super(repository, NotificacaoRequestDTO::toModel, NotificacaoResponseDTO::new);
    }
}
