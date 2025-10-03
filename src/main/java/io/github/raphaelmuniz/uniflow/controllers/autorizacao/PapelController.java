package io.github.raphaelmuniz.uniflow.controllers.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.services.autorizacao.PapelService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/papeis")
@PreAuthorize("hasRole('ADMIN')")
public class PapelController {
    private final PapelService papelService;

    public PapelController(PapelService papelService) {
        this.papelService = papelService;
    }

    @PostMapping
    public ResponseEntity<PapelResponseDTO> criar(@RequestBody @Valid PapelRequestDTO dto) {
        PapelResponseDTO novoPapel = papelService.criar(dto);
        URI location = URI.create("/api/papeis/" + novoPapel.getId());
        return ResponseEntity.created(location).body(novoPapel);
    }

    @GetMapping
    public ResponseEntity<List<PapelResponseDTO>> buscarTodos() {
        return ResponseEntity.ok(papelService.buscarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PapelResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(papelService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PapelResponseDTO> atualizar(@PathVariable String id, @RequestBody @Valid PapelRequestDTO dto) {
        return ResponseEntity.ok(papelService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        papelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
