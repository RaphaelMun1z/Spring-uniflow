package io.github.raphaelmuniz.uniflow.controllers.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaModeloService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assinaturas-modelo")
public class AssinaturaModeloController {
    private final AssinaturaModeloService service;

    protected AssinaturaModeloController(AssinaturaModeloService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<AssinaturaModeloResponseDTO>> buscarTodos() {
        List<AssinaturaModeloResponseDTO> assinaturasModelo = service.findAll();
        return ResponseEntity.ok(assinaturasModelo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AssinaturaModeloResponseDTO> buscarPorId(@PathVariable String id) {
        AssinaturaModeloResponseDTO modelo = service.findById(id);
        return ResponseEntity.ok(modelo);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssinaturaModeloResponseDTO> criarModelo(@RequestBody @Valid AssinaturaModeloRequestDTO dto) {
        AssinaturaModeloResponseDTO novoModelo = service.criar(dto);
        URI location = URI.create("/api/assinaturas-modelo/" + novoModelo.getId());
        return ResponseEntity.created(location).body(novoModelo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AssinaturaModeloResponseDTO> atualizarModelo(@PathVariable String id, @RequestBody @Valid AssinaturaModeloRequestDTO dto) {
        AssinaturaModeloResponseDTO modeloAtualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(modeloAtualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
