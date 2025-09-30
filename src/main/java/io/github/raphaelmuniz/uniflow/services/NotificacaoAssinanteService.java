package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.NotificacaoAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.NotificacaoAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.NotificacaoAssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.NotificacaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoAssinanteService extends GenericCrudServiceImpl<NotificacaoAssinanteRequestDTO, NotificacaoAssinanteResponseDTO, NotificacaoAssinante, String> {
    @Autowired
    NotificacaoAssinanteRepository notificacaoAssinanteRepository;

    @Autowired
    AssinanteRepository assinanteRepository;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    protected NotificacaoAssinanteService(NotificacaoAssinanteRepository repository) {
        super(repository, NotificacaoAssinanteRequestDTO::toModel, NotificacaoAssinanteResponseDTO::new);
    }

    @Override
    public NotificacaoAssinanteResponseDTO create(NotificacaoAssinanteRequestDTO data) {
        Assinante assinante = assinanteRepository.findById(data.getAssinanteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        Notificacao notificacao = notificacaoRepository.findById(data.getNotificacaoId()).orElseThrow(() -> new NotFoundException("Notificação não encontrado."));
        NotificacaoAssinante notificacaoAssinante = new NotificacaoAssinante(null, false, null, assinante, notificacao);
        NotificacaoAssinante saved = notificacaoAssinanteRepository.save(notificacaoAssinante);
        return new NotificacaoAssinanteResponseDTO(saved);
    }
}
