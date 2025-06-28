package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.AtividadeModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.services.AtividadeModeloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/atividades")
public class AtividadeModeloController {
    @Autowired
    AtividadeModeloService service;

    @PostMapping
    public ResponseEntity<AtividadeModeloResponseDTO> create(@RequestBody @Valid AtividadeModeloRequestDTO data) {
        return ResponseEntity.ok(service.create(data));
    }

    @GetMapping
    public ResponseEntity<List<AtividadeModeloResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AtividadeModeloResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
