package io.github.raphaelmuniz.uniflow.controllers.notificacao;

import io.github.raphaelmuniz.uniflow.controllers.notificacao.docs.NotificacaoControllerDocs;
import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoBroadcastRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.notificacao.NotificacaoGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.notificacao.NotificacaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notificacoes")
@PreAuthorize("hasRole('ADMIN')")
public class NotificacaoController implements NotificacaoControllerDocs {
    private final NotificacaoService notificacaoService;

    public NotificacaoController(NotificacaoService service) {
        this.notificacaoService = service;
    }

    @PostMapping("/enviar-para-grupo")
    @PreAuthorize("hasAuthority('NOTIFICACAO_CRIAR')")
    @Override
    public ResponseEntity<NotificacaoResponseDTO> enviarParaGrupo(
        @RequestBody @Valid NotificacaoGrupoRequestDTO dto,
        @AuthenticationPrincipal Usuario remetente) {
        NotificacaoResponseDTO notificacao = notificacaoService.criarNotificacaoParaGrupo(dto, remetente);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacao);
    }

    @PostMapping("/enviar-broadcast")
    @PreAuthorize("hasAuthority('NOTIFICACAO_CRIAR')")
    @Override
    public ResponseEntity<NotificacaoResponseDTO> enviarBroadcast(
        @RequestBody @Valid NotificacaoBroadcastRequestDTO dto,
        @AuthenticationPrincipal Usuario remetente) {
        NotificacaoResponseDTO notificacao = notificacaoService.criarNotificacaoBroadcast(dto, remetente);
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacao);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NOTIFICACAO_LER')")
    @Override
    public ResponseEntity<PaginatedResponse<NotificacaoResponseDTO>> buscarTodas(Pageable pageable) {
        Page<NotificacaoResponseDTO> page = notificacaoService.buscarTodas(pageable);
        PaginatedResponse<NotificacaoResponseDTO> response = new PaginatedResponse<>(
            page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }
}