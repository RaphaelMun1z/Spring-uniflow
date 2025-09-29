package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.dto.res.AtividadeAssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.PagamentoProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Atividade;
import io.github.raphaelmuniz.uniflow.entities.Usuario;
import io.github.raphaelmuniz.uniflow.services.AssinaturaUsuarioService;
import io.github.raphaelmuniz.uniflow.services.AtividadeAssinanteService;
import io.github.raphaelmuniz.uniflow.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProfileController {
    @Autowired
    AssinaturaUsuarioService assinaturaUsuarioService;

    @Autowired
    PagamentoService pagamentoService;

    @Autowired
    AtividadeAssinanteService atividadeAssinanteService;

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
    public ResponseEntity<List<AtividadeAssinanteResponseDTO>> getMinhasAtividades(@AuthenticationPrincipal Usuario usuarioLogado) {
        List<AtividadeAssinanteResponseDTO> atividades = atividadeAssinanteService.findByAssinanteDonoId(usuarioLogado.getId());
        return ResponseEntity.ok(atividades);
    }
}
