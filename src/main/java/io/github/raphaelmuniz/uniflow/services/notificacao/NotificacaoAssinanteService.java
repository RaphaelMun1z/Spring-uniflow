package io.github.raphaelmuniz.uniflow.services.notificacao;

import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.NotificacaoAssinanteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificacaoAssinanteService {
    private final NotificacaoAssinanteRepository notificacaoAssinanteRepository;

    public NotificacaoAssinanteService(NotificacaoAssinanteRepository notificacaoAssinanteRepository) {
        this.notificacaoAssinanteRepository = notificacaoAssinanteRepository;
    }

    @Transactional(readOnly = true)
    public Page<NotificacaoResponseDTO> buscarMinhasNotificacoes(Usuario usuarioLogado, Pageable pageable) {
        Page<NotificacaoAssinante> notificacoes = notificacaoAssinanteRepository
                .findByDestinatario_IdOrderByNotificacao_DataCriacaoDesc(usuarioLogado.getId(), pageable);
        return notificacoes.map(NotificacaoResponseDTO::new);
    }

    @Transactional
    public NotificacaoResponseDTO marcarComoLida(String notificacaoId, Usuario usuarioLogado) {
        NotificacaoAssinante notificacao = buscarNotificacaoDoUsuario(notificacaoId, usuarioLogado);
        notificacao.setLida(true);
        notificacao.setDataLeitura(java.time.LocalDateTime.now());
        NotificacaoAssinante notificacaoSalva = notificacaoAssinanteRepository.save(notificacao);
        return new NotificacaoResponseDTO(notificacaoSalva);
    }

    @Transactional
    public void marcarTodasComoLidas(Usuario usuarioLogado) {
        List<NotificacaoAssinante> notificacoesNaoLidas = notificacaoAssinanteRepository
                .findAllByDestinatario_IdAndLidaIsFalse(usuarioLogado.getId());

        if (!notificacoesNaoLidas.isEmpty()) {
            notificacoesNaoLidas.forEach(notificacao -> {
                notificacao.setLida(true);
                notificacao.setDataLeitura(java.time.LocalDateTime.now());
            });
            notificacaoAssinanteRepository.saveAll(notificacoesNaoLidas);
        }
    }

    @Transactional
    public void deletarNotificacao(String notificacaoId, Usuario usuarioLogado) {
        NotificacaoAssinante notificacao = buscarNotificacaoDoUsuario(notificacaoId, usuarioLogado);
        notificacaoAssinanteRepository.delete(notificacao);
    }

    private NotificacaoAssinante buscarNotificacaoDoUsuario(String notificacaoId, Usuario usuarioLogado) {
        NotificacaoAssinante notificacao = notificacaoAssinanteRepository.findById(notificacaoId)
                .orElseThrow(() -> new NotFoundException("Notificação não encontrada."));

        if (!notificacao.getDestinatario().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Você não tem permissão para acessar esta notificação.");
        }
        return notificacao;
    }
}
