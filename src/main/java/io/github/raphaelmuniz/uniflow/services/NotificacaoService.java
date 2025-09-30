package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.NotificacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.NotificacoesListResponse;
import io.github.raphaelmuniz.uniflow.entities.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.NotificacaoAssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.NotificacaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    public NotificacoesListResponse getNotificacoesByAssinanteId(String assinanteId) {
        long totalElementos = notificacaoAssinanteRepository.countAllByAssinante_Id(assinanteId);

        List<NotificacaoAssinante> notificacoesAssinante =
                notificacaoAssinanteRepository.findByAssinanteId(assinanteId);

        List<NotificacaoResponseDTO> list = notificacoesAssinante.stream()
                .map(NotificacaoResponseDTO::new)
                .toList();

        NotificacoesListResponse notificacoesListResponse = new NotificacoesListResponse(list, totalElementos);
        return notificacoesListResponse;
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
