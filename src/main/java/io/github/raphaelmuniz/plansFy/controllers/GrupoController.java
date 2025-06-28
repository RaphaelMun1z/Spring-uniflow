package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteEmGrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
    @Autowired
    GrupoService service;

    @PostMapping
    public ResponseEntity<GrupoResponseDTO> create(@RequestBody @Valid GrupoRequestDTO data) {
        return ResponseEntity.ok(service.create(data));
    }

    @GetMapping
    public ResponseEntity<List<GrupoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/adicionar/aluno")
//    public ResponseEntity<Void> adicionarAluno(@RequestBody @Valid EstudanteEmGrupoRequestDTO data) {
//        service.adicionarAluno(data);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/remover/aluno")
//    public ResponseEntity<Void> removerAluno(@RequestBody @Valid EstudanteEmGrupoRequestDTO data) {
//        service.removerAluno(data);
//        return ResponseEntity.ok().build();
//    }
}
