package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.DisciplinaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.DisciplinaResponseDTO;
import io.github.raphaelmuniz.plansFy.services.DisciplinaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController {
    @Autowired
    DisciplinaService service;

    @PostMapping
    public ResponseEntity<DisciplinaResponseDTO> create(@RequestBody @Valid DisciplinaRequestDTO data) {
        return ResponseEntity.ok(service.create(data));
    }

    @GetMapping
    public ResponseEntity<List<DisciplinaResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplinaResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

//    @GetMapping("/{id}/atividades")
//    public ResponseEntity<List<AtividadeModeloResponseDTO>> findAll(@PathVariable String id) {
//        return ResponseEntity.ok(service.listarAtividadesDisciplina(id));
//    }
}
