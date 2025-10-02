package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.ProfileUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.*;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.NotificacoesProfileResponse;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.ProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.ProfileService;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaUsuarioService;
import io.github.raphaelmuniz.uniflow.services.assinatura.PagamentoService;
import io.github.raphaelmuniz.uniflow.services.notificacao.NotificacaoService;
import io.github.raphaelmuniz.uniflow.services.usuario.AssinanteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
public class ProfileController {
    private final ProfileService profileService;
    private final AssinanteService assinanteService;
    private final AssinaturaUsuarioService assinaturaUsuarioService;
    private final PagamentoService pagamentoService;
    private final NotificacaoService notificacaoService;

    public ProfileController(
            ProfileService profileService,
            AssinanteService assinanteService,
            AssinaturaUsuarioService assinaturaUsuarioService,
            PagamentoService pagamentoService,
            NotificacaoService notificacaoService
    ) {
        this.profileService = profileService;
        this.assinanteService = assinanteService;
        this.assinaturaUsuarioService = assinaturaUsuarioService;
        this.pagamentoService = pagamentoService;
        this.notificacaoService = notificacaoService;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponseDTO> obterMeuPerfil(@AuthenticationPrincipal Usuario usuarioLogado) {
        ProfileResponseDTO perfil = profileService.getAuthenticatedUserProfile(usuarioLogado);
        return ResponseEntity.ok(perfil);
    }

    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProfileResponseDTO> atualizarMeuPerfil(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestBody @Valid ProfileUpdateRequestDTO dto
    ) {
        ProfileResponseDTO perfilAtualizado = profileService.atualizarPerfilUsuario(usuarioLogado, dto);
        return ResponseEntity.ok(perfilAtualizado);
    }

    @GetMapping("/assinaturas")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginatedResponse<AssinaturaProfileResponseDTO>> obterMinhasAssinaturas(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PageableDefault(size = 5, sort = "dataInicio", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<AssinaturaProfileResponseDTO> page = assinaturaUsuarioService.findByAssinanteId(usuarioLogado.getId(), pageable);
        PaginatedResponse<AssinaturaProfileResponseDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pagamentos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginatedResponse<PagamentoResponseDTO>> obterMeusPagamentos(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PageableDefault(size = 10, sort = "dataPagamento", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PagamentoResponseDTO> page = pagamentoService.listarHistoricoDePagamentos(usuarioLogado.getId(), pageable);
        PaginatedResponse<PagamentoResponseDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grupos")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PaginatedResponse<GruposProfileResponseDTO>> obterMeusGrupos(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @PageableDefault(size = 10, sort = "grupo.titulo", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Page<GruposProfileResponseDTO> page = assinanteService.obterGruposPorAssinanteId(usuarioLogado.getId(), pageable);
        PaginatedResponse<GruposProfileResponseDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/notificacoes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NotificacoesProfileResponse> obterMinhasNotificacoes(@AuthenticationPrincipal Usuario usuarioLogado) {
        NotificacoesProfileResponse notificacoes = notificacaoService.getNotificacoesByAssinanteId(usuarioLogado.getId());
        return ResponseEntity.ok(notificacoes);
    }
}
