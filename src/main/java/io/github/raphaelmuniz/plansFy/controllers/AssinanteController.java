package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AssinanteRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.services.AssinanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/assinantes")
public class AssinanteController extends GenericCrudControllerImpl<AssinanteRequestDTO, AssinanteResponseDTO> {
    private final AssinanteService assinanteService;

    protected AssinanteController(AssinanteService service) {
        super(service);
        this.assinanteService = service;
    }

    @GetMapping("/{assinanteId}/grupos")
    public ResponseEntity<List<GrupoResponseDTO>> listarGruposInscritosPeloAssinante(@PathVariable String assinanteId) {
        return ResponseEntity.ok(assinanteService.findGruposByAssinante(assinanteId));
    }
}
