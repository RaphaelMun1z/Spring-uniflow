package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.services.AssinanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/assinantes")
public class AssinanteController extends GenericCrudControllerImpl<AssinanteRequestDTO, AssinanteResponseDTO> {
    private final AssinanteService assinanteService;

    protected AssinanteController(AssinanteService service) {
        super(service);
        this.assinanteService = service;
    }

    @GetMapping("/{assinanteId}/grupos")
    public ResponseEntity<List<GruposProfileResponseDTO>> listarGruposInscritosPeloAssinante(@PathVariable String assinanteId) {
        return ResponseEntity.ok(assinanteService.findGruposByAssinante(assinanteId));
    }

    @GetMapping("/{assinanteId}/assinatura-vigente")
    public ResponseEntity<AssinaturaUsuarioResponseDTO> getAssinaturaVigente(@PathVariable String assinanteId) {
        AssinaturaUsuarioResponseDTO assinatura = assinanteService.getAssinaturaVigente(assinanteId);
        return ResponseEntity.ok(assinatura);
    }
}
