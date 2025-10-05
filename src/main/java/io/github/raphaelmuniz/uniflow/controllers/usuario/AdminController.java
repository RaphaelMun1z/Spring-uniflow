package io.github.raphaelmuniz.uniflow.controllers.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PermissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinantePublicProfileDTO;
import io.github.raphaelmuniz.uniflow.services.usuario.AdminService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController implements io.github.raphaelmuniz.uniflow.controllers.usuario.docs.AdminControllerDocs {
    private final AdminService adminService;

    public AdminController(AdminService service) {
        this.adminService = service;
    }

    @GetMapping("/usuarios")
    @PreAuthorize("hasAuthority('ASSINANTE_LER')")
    @Override
    public ResponseEntity<PaginatedResponse<AssinantePublicProfileDTO>> listarUsuarios(
        @RequestParam(required = false) String tipo,
        Pageable pageable) {
        Page<AssinantePublicProfileDTO> page = adminService.listarUsuarios(tipo, pageable);

        PaginatedResponse<AssinantePublicProfileDTO> response = new PaginatedResponse<>(
            page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/planos")
    @PreAuthorize("hasAuthority('ASSINATURA_MODELO_LER')")
    @Override
    public ResponseEntity<List<AssinaturaModeloResponseDTO>> listarPlanos() {
        return ResponseEntity.ok(adminService.listarPlanos());
    }

    @PostMapping("/planos")
    @PreAuthorize("hasAuthority('ASSINATURA_MODELO_CRIAR')")
    @Override
    public ResponseEntity<AssinaturaModeloResponseDTO> criarPlano(@RequestBody @Valid AssinaturaModeloRequestDTO dto) {
        AssinaturaModeloResponseDTO novoPlano = adminService.criarPlano(dto);
        URI location = URI.create("/api/assinaturas-modelo/" + novoPlano.id());
        return ResponseEntity.created(location).body(novoPlano);
    }

    @PutMapping("/planos/{id}")
    @PreAuthorize("hasAuthority('ASSINATURA_MODELO_EDITAR')")
    @Override
    public ResponseEntity<AssinaturaModeloResponseDTO> atualizarPlano(@PathVariable String id, @RequestBody @Valid AssinaturaModeloRequestDTO dto) {
        return ResponseEntity.ok(adminService.atualizarPlano(id, dto));
    }

    @DeleteMapping("/planos/{id}")
    @PreAuthorize("hasAuthority('ASSINATURA_MODELO_DELETAR')")
    @Override
    public ResponseEntity<Void> deletarPlano(@PathVariable String id) {
        adminService.deletarPlano(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/papeis")
    @PreAuthorize("hasAuthority('PAPEL_LER')")
    @Override
    public ResponseEntity<List<PapelResponseDTO>> listarPapeis() {
        return ResponseEntity.ok(adminService.listarPapeis());
    }

    @PostMapping("/papeis")
    @PreAuthorize("hasAuthority('PAPEL_CRIAR')")
    @Override
    public ResponseEntity<PapelResponseDTO> criarPapel(@RequestBody @Valid PapelRequestDTO dto) {
        PapelResponseDTO novoPapel = adminService.criarPapel(dto);
        URI location = URI.create("/api/papeis/" + novoPapel.id());
        return ResponseEntity.created(location).body(novoPapel);
    }

    @GetMapping("/permissoes")
    @PreAuthorize("hasAuthority('PERMISSAO_LER')")
    @Override
    public ResponseEntity<List<PermissaoResponseDTO>> listarPermissoes() {
        return ResponseEntity.ok(adminService.listarPermissoes());
    }

    @GetMapping("/assinaturas-usuario")
    @PreAuthorize("hasAuthority('ASSINATURA_USUARIO_LER')")
    @Override
    public ResponseEntity<PaginatedResponse<AssinaturaUsuarioResponseDTO>> listarAssinaturasDeUsuarios(Pageable pageable) {
        Page<AssinaturaUsuarioResponseDTO> page = adminService.listarAssinaturasDeUsuarios(pageable);
        PaginatedResponse<AssinaturaUsuarioResponseDTO> response = new PaginatedResponse<>(
            page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/assinaturas-usuario")
    @PreAuthorize("hasAuthority('ASSINATURA_USUARIO_CRIAR')")
    @Override
    public ResponseEntity<AssinaturaUsuarioResponseDTO> criarAssinaturaParaUsuario(@RequestBody @Valid AssinaturaUsuarioRequestDTO dto) {
        AssinaturaUsuarioResponseDTO novaAssinatura = adminService.criarAssinaturaParaUsuario(dto);
        URI location = URI.create("/api/admin/assinaturas-usuario/" + novaAssinatura.id());
        return ResponseEntity.created(location).body(novaAssinatura);
    }

    @PatchMapping("/assinaturas-usuario/{id}/cancelar")
    @PreAuthorize("hasAuthority('ASSINATURA_USUARIO_CANCELAR')")
    @Override
    public ResponseEntity<AssinaturaUsuarioResponseDTO> cancelarAssinaturaDeUsuario(@PathVariable String id) {
        return ResponseEntity.ok(adminService.cancelarAssinaturaDeUsuario(id));
    }
}
