package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.controllers.grupo.docs.DisciplinaControllerDocs;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.services.grupo.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/disciplinas")
public class DisciplinaController implements DisciplinaControllerDocs {

    private final DisciplinaService disciplinaService;

    public DisciplinaController(DisciplinaService disciplinaService) {
        this.disciplinaService = disciplinaService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('DISCIPLINA_CRIAR')")
    @Override
    public ResponseEntity<DisciplinaResponseDTO> criar(@RequestBody @Valid DisciplinaRequestDTO dto) {
        DisciplinaResponseDTO novaDisciplina = disciplinaService.criar(dto);
        URI location = URI.create("/api/disciplinas/" + novaDisciplina.id());
        return ResponseEntity.created(location).body(novaDisciplina);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<PaginatedResponse<DisciplinaResponseDTO>> buscarTodas(Pageable pageable) {
        Page<DisciplinaResponseDTO> page = disciplinaService.buscarTodas(pageable);
        PaginatedResponse<DisciplinaResponseDTO> response = new PaginatedResponse<>(
            page.getContent(), page.getNumber(), page.getTotalPages(), page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<DisciplinaResponseDTO> buscarPorId(@PathVariable String id) {
        return ResponseEntity.ok(disciplinaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DISCIPLINA_EDITAR')")
    @Override
    public ResponseEntity<DisciplinaResponseDTO> atualizar(@PathVariable String id, @RequestBody @Valid DisciplinaRequestDTO dto) {
        return ResponseEntity.ok(disciplinaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DISCIPLINA_DELETAR')")
    @Override
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        disciplinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}