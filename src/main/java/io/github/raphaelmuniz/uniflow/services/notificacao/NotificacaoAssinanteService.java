package io.github.raphaelmuniz.uniflow.services.notificacao;

import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoAssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.NotificacaoAssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.NotificacaoRepository;
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
