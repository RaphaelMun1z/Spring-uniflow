package io.github.raphaelmuniz.uniflow.services.notificacao;

import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoBroadcastRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO; // Reutilizando o DTO que já temos
import io.github.raphaelmuniz.uniflow.entities.enums.CategoriaNotificacaoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.notificacao.NotificacaoAssinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.NotificacaoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NotificacaoService {
    private final NotificacaoRepository notificacaoRepository;
    private final GrupoRepository grupoRepository;
    private final AssinanteRepository assinanteRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository, GrupoRepository grupoRepository, AssinanteRepository assinanteRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.grupoRepository = grupoRepository;
        this.assinanteRepository = assinanteRepository;
    }

    @Transactional
    public NotificacaoResponseDTO criarNotificacaoParaGrupo(NotificacaoGrupoRequestDTO dto, Usuario remetente) {
        Grupo grupo = grupoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado com o ID: " + dto.grupoId()));

        List<Assinante> destinatarios = grupo.getInscricoes().stream()
                .map(inscricao -> (Assinante) inscricao.getMembro())
                .toList();

        return criarEEnviar(dto.mensagem(), dto.categoria(), dto.link(), (Assinante) remetente, destinatarios);
    }

    @Transactional
    public NotificacaoResponseDTO criarNotificacaoBroadcast(NotificacaoBroadcastRequestDTO dto, Usuario remetente) {
        List<Assinante> todosOsAssinantes = assinanteRepository.findAll();
        return criarEEnviar(dto.mensagem(), dto.categoria(), dto.link(), (Assinante) remetente, todosOsAssinantes);
    }

    @Transactional(readOnly = true)
    public Page<NotificacaoResponseDTO> buscarTodas(Pageable pageable) {
        Page<Notificacao> notificacoes = notificacaoRepository.findAll(pageable);
        return notificacoes.map(n -> new NotificacaoResponseDTO(Objects.requireNonNull(n.getDestinatarios().stream().findFirst().orElse(null))));
    }

    private NotificacaoResponseDTO criarEEnviar(String mensagem, CategoriaNotificacaoEnum categoria, String link, Assinante remetente, List<Assinante> destinatarios) {
        Notificacao notificacao = new Notificacao();
        notificacao.setMensagem(mensagem);
        notificacao.setCategoria(categoria);
        notificacao.setLink(link);
        notificacao.setRemetente(remetente);

        Set<NotificacaoAssinante> notificacoesAssinante = destinatarios.stream().map(destinatario -> {
            NotificacaoAssinante na = new NotificacaoAssinante();
            na.setDestinatario(destinatario);
            na.setNotificacao(notificacao);
            na.setLida(false);
            return na;
        }).collect(Collectors.toSet());

        notificacao.setDestinatarios(notificacoesAssinante);

        Notificacao notificacaoSalva = notificacaoRepository.save(notificacao);

        return new NotificacaoResponseDTO(Objects.requireNonNull(notificacaoSalva.getDestinatarios().stream().findFirst().orElse(null)));
    }
}
