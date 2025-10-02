package io.github.raphaelmuniz.uniflow.controllers.usuario;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.usuario.AssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinantePublicProfileDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.services.usuario.AssinanteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assinantes")
public class AssinanteController extends GenericCrudControllerImpl<AssinanteRequestDTO, AssinanteResponseDTO> {
    private final AssinanteService assinanteService;

    protected AssinanteController(AssinanteService service) {
        super(service);
        this.assinanteService = service;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AssinantePublicProfileDTO> buscarPerfilPublico(@PathVariable String id) {
        AssinantePublicProfileDTO perfil = assinanteService.buscarPerfilPublicoPorId(id);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginatedResponse<AssinantePublicProfileDTO>> listarAssinantes(Pageable pageable) {
        Page<AssinantePublicProfileDTO> page = assinanteService.listarTodosPerfisPublicos(pageable);
        PaginatedResponse<AssinantePublicProfileDTO> response = new PaginatedResponse<>(
                page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }
}
