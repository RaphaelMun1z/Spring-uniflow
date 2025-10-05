package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.ProfileUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.ProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.ProfileService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/me")
@PreAuthorize("isAuthenticated()")
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<ProfileResponseDTO> obterMeuPerfil(@AuthenticationPrincipal Usuario usuarioLogado) {
        ProfileResponseDTO perfil = profileService.buscarPerfilDoUsuarioAutenticado(usuarioLogado);
        return ResponseEntity.ok(perfil);
    }

    @PatchMapping
    public ResponseEntity<ProfileResponseDTO> atualizarMeuPerfil(
            @AuthenticationPrincipal Usuario usuarioLogado,
            @RequestBody @Valid ProfileUpdateRequestDTO dto) {
        ProfileResponseDTO perfilAtualizado = profileService.atualizarPerfilUsuario(usuarioLogado, dto);
        return ResponseEntity.ok(perfilAtualizado);
    }

    @GetMapping("/assinaturas")
    public ResponseEntity<PaginatedResponse<AssinaturaProfileResponseDTO>> obterMinhasAssinaturas(
            @AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable) {
        Page<AssinaturaProfileResponseDTO> page = profileService.buscarMinhasAssinaturas(usuarioLogado.getId(), pageable);
        PaginatedResponse<AssinaturaProfileResponseDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pagamentos")
    public ResponseEntity<PaginatedResponse<PagamentoResponseDTO>> obterMeusPagamentos(
            @AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable) {
        Page<PagamentoResponseDTO> page = profileService.buscarMeusPagamentos(usuarioLogado.getId(), pageable);
        PaginatedResponse<PagamentoResponseDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/grupos")
    public ResponseEntity<PaginatedResponse<GruposProfileResponseDTO>> obterMeusGrupos(
            @AuthenticationPrincipal Usuario usuarioLogado, Pageable pageable) {
        Page<GruposProfileResponseDTO> page = profileService.buscarMeusGrupos(usuarioLogado.getId(), pageable);
        PaginatedResponse<GruposProfileResponseDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> cancelarMinhaConta(@AuthenticationPrincipal Usuario usuarioLogado) {
        profileService.cancelarConta(usuarioLogado);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/assinatura-ativa")
    public ResponseEntity<AssinaturaUsuarioResponseDTO> cancelarMinhaAssinatura(@AuthenticationPrincipal Usuario usuarioLogado) {
        AssinaturaUsuarioResponseDTO assinaturaCancelada = profileService.cancelarMinhaAssinatura(usuarioLogado);
        return ResponseEntity.ok(assinaturaCancelada);
    }
}