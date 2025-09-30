package io.github.raphaelmuniz.uniflow.services.notificacao;

import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.NotificacoesProfileResponse;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.NotificacaoAssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.NotificacaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService extends GenericCrudServiceImpl<NotificacaoRequestDTO, NotificacaoResponseDTO, Notificacao, String> {
    @Autowired
    NotificacaoAssinanteRepository notificacaoAssinanteRepository;

    protected NotificacaoService(NotificacaoRepository repository) {
        super(repository, NotificacaoRequestDTO::toModel, NotificacaoResponseDTO::new);
    }

    public NotificacoesProfileResponse getNotificacoesByAssinanteId(String assinanteId) {
        long totalElementos = notificacaoAssinanteRepository.countAllByAssinanteNotificadoId(assinanteId);

        List<NotificacaoAssinante> notificacoesAssinante =
                notificacaoAssinanteRepository.findByAssinanteNotificadoId(assinanteId);

        List<NotificacaoResponseDTO> list = notificacoesAssinante.stream()
                .map(NotificacaoResponseDTO::new)
                .toList();

        return new NotificacoesProfileResponse(list, totalElementos);
    }

    @Transactional
    public void marcarNotificacaoComoLida(String notificacaoAssinanteId, String assinanteId) {
        int linhasAfetadas = notificacaoAssinanteRepository.marcarComoLida(
                notificacaoAssinanteId,
                assinanteId,
                LocalDateTime.now()
        );

        if (linhasAfetadas == 0) {
            throw new NotFoundException("Notificação não encontrada ou já lida.");
        }
    }
}
