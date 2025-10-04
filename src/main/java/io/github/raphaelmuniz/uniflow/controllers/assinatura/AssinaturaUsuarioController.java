package io.github.raphaelmuniz.uniflow.controllers.assinatura;

import io.github.raphaelmuniz.uniflow.controllers.assinatura.docs.AssinaturaUsuarioControllerDocs;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaUsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/assinaturas-usuario")
@Tag(name = "Assinatura Usuário", description = "Endpoints para gerenciar assinaturas dos usuários")
public class AssinaturaUsuarioController implements AssinaturaUsuarioControllerDocs {
    private final AssinaturaUsuarioService service;

    public AssinaturaUsuarioController(AssinaturaUsuarioService service) {
        this.service = service;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ASSINATURA_USUARIO_CRIAR')")
    @Override
    public ResponseEntity<AssinaturaUsuarioResponseDTO> criarAssinaturaParaUsuario(@RequestBody @Valid AssinaturaUsuarioRequestDTO dto) {
        AssinaturaUsuarioResponseDTO novaAssinatura = service.criarAssinatura(dto);
        URI location = URI.create("/api/assinaturas-usuario/" + novaAssinatura.id());
        return ResponseEntity.created(location).body(novaAssinatura);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ASSINATURA_USUARIO_LER')")
    @Override
    public ResponseEntity<PaginatedResponse<AssinaturaUsuarioResponseDTO>> listarTodas(Pageable pageable) {
        Page<AssinaturaUsuarioResponseDTO> page = service.buscarTodas(pageable);
        PaginatedResponse<AssinaturaUsuarioResponseDTO> response = new PaginatedResponse<>(
            page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ASSINATURA_USUARIO_LER')")
    @Override
    public ResponseEntity<AssinaturaUsuarioResponseDTO> buscarPorId(@PathVariable String id) {
        AssinaturaUsuarioResponseDTO assinatura = service.buscarPorId(id);
        return ResponseEntity.ok(assinatura);
    }

    @PatchMapping("/{id}/cancelar")
    @PreAuthorize("hasAuthority('ASSINATURA_USUARIO_EDITAR')")
    @Override
    public ResponseEntity<AssinaturaUsuarioResponseDTO> cancelarAssinatura(@PathVariable String id) {
        AssinaturaUsuarioResponseDTO assinaturaCancelada = service.cancelar(id);
        return ResponseEntity.ok(assinaturaCancelada);
    }
}
