package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.AdicionarMembroGrupoDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResumeResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.services.grupo.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grupos")
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
    public ResponseEntity<List<AtividadeAvaliativaResponseDTO>> listarAtividades(@PathVariable String grupoId) {
        List<AtividadeAvaliativaResponseDTO> atividades = service.listarAtividades(grupoId);
        return ResponseEntity.ok(atividades);
    }
}
