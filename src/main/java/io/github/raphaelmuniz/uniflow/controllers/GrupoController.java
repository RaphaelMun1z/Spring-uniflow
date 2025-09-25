package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.AdicionarMembroGrupoDTO;
import io.github.raphaelmuniz.uniflow.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResumeResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController extends GenericCrudControllerImpl<GrupoRequestDTO, GrupoResponseDTO> {
    @Autowired
    GrupoService service;

    protected GrupoController(GrupoService service) {
        super(service);
    }

    @PostMapping("/adicionar/membro")
    public ResponseEntity<Void> adicionarMembro(@RequestBody @Valid AdicionarMembroGrupoDTO data) {
        service.adicionarIntegrantes(data);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{grupoId}/remover/{membroId}")
    public ResponseEntity<Void> removerMembro(@PathVariable String grupoId, @PathVariable String membroId) {
        service.removerIntegrante(grupoId, membroId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{grupoId}/membros")
    public ResponseEntity<List<AssinanteResumeResponseDTO>> listarMembros(@PathVariable String grupoId) {
        List<AssinanteResumeResponseDTO> membros = service.listarMembros(grupoId);
        return ResponseEntity.ok(membros);
    }

    @GetMapping("/{grupoId}/atividades")
    public ResponseEntity<List<AtividadeGrupoResponseDTO>> listarAtividades(@PathVariable String grupoId) {
        List<AtividadeGrupoResponseDTO> atividades = service.listarAtividades(grupoId);
        return ResponseEntity.ok(atividades);
    }
}
