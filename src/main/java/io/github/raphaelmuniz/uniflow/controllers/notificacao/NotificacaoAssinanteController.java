package io.github.raphaelmuniz.uniflow.controllers.notificacao;

import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.notificacao.NotificacaoAssinanteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacoes-assinante")
@PreAuthorize("isAuthenticated()")
public class NotificacaoAssinanteController {
    private final NotificacaoAssinanteService service;

    public NotificacaoAssinanteController(NotificacaoAssinanteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<NotificacaoResponseDTO>> buscarMinhasNotificacoes(
            @AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable) {
        Page<NotificacaoResponseDTO> page = service.buscarMinhasNotificacoes(usuarioLogado, pageable);
        PaginatedResponse<NotificacaoResponseDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/ler")
    public ResponseEntity<NotificacaoResponseDTO> marcarComoLida(
            @PathVariable("id") String notificacaoId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        NotificacaoResponseDTO notificacaoAtualizada = service.marcarComoLida(notificacaoId, usuarioLogado);
        return ResponseEntity.ok(notificacaoAtualizada);
    }

    @PostMapping("/marcar-todas-como-lidas")
    public ResponseEntity<Void> marcarTodasComoLidas(@AuthenticationPrincipal Usuario usuarioLogado) {
        service.marcarTodasComoLidas(usuarioLogado);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNotificacao(
            @PathVariable("id") String notificacaoId,
            @AuthenticationPrincipal Usuario usuarioLogado) {
        service.deletarNotificacao(notificacaoId, usuarioLogado);
        return ResponseEntity.noContent().build();
    }
}
