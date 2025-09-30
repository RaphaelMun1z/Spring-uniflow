package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.dto.req.profile.AtividadeAssinanteStatusPatchRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.*;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.NotificacoesListResponse;
import io.github.raphaelmuniz.uniflow.entities.Usuario;
import io.github.raphaelmuniz.uniflow.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    AssinaturaUsuarioService assinaturaUsuarioService;

    @Autowired
    PagamentoService pagamentoService;

    @Autowired
    AtividadeEstudanteService atividadeEstudanteService;

    @Autowired
    AssinanteService assinanteService;

    @Autowired
    NotificacaoService notificacaoService;

    @GetMapping("/me/assinatura")
    @PreAuthorize("isAuthenticated() and (authentication.principal.hasRole('ESTUDANTE') or authentication.principal.hasRole('PROFESSOR'))")
    public ResponseEntity<AssinaturaProfileResponseDTO> getMinhaAssinatura(@AuthenticationPrincipal Usuario usuarioLogado) {
        AssinaturaProfileResponseDTO assinatura = assinaturaUsuarioService.findByAssinanteId(usuarioLogado.getId());
        return ResponseEntity.ok(assinatura);
    }

    @GetMapping("/me/pagamentos")
    @PreAuthorize("isAuthenticated() and (authentication.principal.hasRole('ESTUDANTE') or authentication.principal.hasRole('PROFESSOR'))")
    public ResponseEntity<PaginatedResponse<PagamentoResponseDTO>> getMeusPagamentos(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PageableDefault(size = 10, sort = "dataPagamento", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PagamentoResponseDTO> page = pagamentoService.listarHistoricoDePagamentos(usuarioLogado.getId(), pageable);
        PaginatedResponse<PagamentoResponseDTO> response = new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me/minhas-atividades")
    @PreAuthorize("isAuthenticated() and authentication.principal.hasRole('ESTUDANTE')")
    public ResponseEntity<List<AtividadeEstudanteResponseDTO>> getMinhasAtividades(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        List<AtividadeEstudanteResponseDTO> atividades = atividadeEstudanteService.findByEstudanteDonoId(usuarioLogado.getId());
        return ResponseEntity.ok(atividades);
    }

    @PatchMapping("/me/minhas-atividades/{atividadeId}")
    @PreAuthorize("isAuthenticated() and authentication.principal.hasRole('ESTUDANTE')")
    public ResponseEntity<Void> atualizaStatusMinhaAtividade(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PathVariable String atividadeId,
            @RequestBody @Valid AtividadeAssinanteStatusPatchRequestDTO requestDTO
    ) {
        atividadeEstudanteService.atualizarStatus(
                usuarioLogado,
                atividadeId,
                requestDTO.getStatus()
        );
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me/meus-grupos")
    @PreAuthorize("isAuthenticated() and (authentication.principal.hasRole('ESTUDANTE') or authentication.principal.hasRole('PROFESSOR'))")
    public ResponseEntity<List<GruposProfileResponseDTO>> getMeusGrupos(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        List<GruposProfileResponseDTO> grupos = assinanteService.findGruposByAssinante(usuarioLogado.getId());
        return ResponseEntity.ok(grupos);
    }

    @GetMapping("/me/minhas-notificacoes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificacoesListResponse> getMinhasNotificacoes(
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        NotificacoesListResponse minhasNotificacoes = notificacaoService.getNotificacoesByAssinanteId(usuarioLogado.getId());
        return ResponseEntity.ok(minhasNotificacoes);
    }

    @PatchMapping("/me/notificacoes/{notificacaoId}/marcar-como-lida")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificacoesListResponse> marcarNotificacaoComoLida(
            @PathVariable String notificacaoId,
            @AuthenticationPrincipal Usuario usuarioLogado
    ) {
        String assinanteId = usuarioLogado.getId();
        notificacaoService.marcarNotificacaoComoLida(notificacaoId, assinanteId);
        NotificacoesListResponse minhasNotificacoesAtualizadas = notificacaoService.getNotificacoesByAssinanteId(assinanteId);
        return ResponseEntity.ok(minhasNotificacoesAtualizadas);
    }
}
