package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.EstudanteRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.EstudanteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudantes")
public class EstudanteController {
    @Autowired
    EstudanteService service;

    @PostMapping
    public ResponseEntity<EstudanteResponseDTO> create(@RequestBody @Valid EstudanteRequestDTO data) {
        return ResponseEntity.ok(service.create(data));
    }

    @GetMapping
    public ResponseEntity<List<EstudanteResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudanteResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/grupos")
    public ResponseEntity<List<GrupoResponseDTO>> listarGruposInscritosPeloAluno(@PathVariable String id) {
        return ResponseEntity.ok(service.listarGruposInscritosPeloAluno(id));
    }
}
