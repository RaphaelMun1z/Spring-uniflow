package io.github.raphaelmuniz.uniflow.controllers.usuario;

import io.github.raphaelmuniz.uniflow.controllers.usuario.docs.AssinanteControllerDocs;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinantePublicProfileDTO;
import io.github.raphaelmuniz.uniflow.services.usuario.AssinanteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assinantes")
public class AssinanteController implements AssinanteControllerDocs {

    private final AssinanteService assinanteService;

    public AssinanteController(AssinanteService service) {
        this.assinanteService = service;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<AssinantePublicProfileDTO> buscarPerfilPublico(@PathVariable String id) {
        AssinantePublicProfileDTO perfil = assinanteService.buscarPerfilPublicoPorId(id);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ASSINANTE_LER')")
    @Override
    public ResponseEntity<PaginatedResponse<AssinantePublicProfileDTO>> listarAssinantes(@RequestParam(required = false) String tipo, Pageable pageable) {
        Page<AssinantePublicProfileDTO> page = assinanteService.listarTodosPerfisPublicos(tipo, pageable);
        PaginatedResponse<AssinantePublicProfileDTO> response = new PaginatedResponse<>(page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements());
        return ResponseEntity.ok(response);
    }
}