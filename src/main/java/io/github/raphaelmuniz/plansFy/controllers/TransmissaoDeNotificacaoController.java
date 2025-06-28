package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.TransmissaoDeNotificacaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.TransmissaoDeNotificacaoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.TransmissaoDeNotificacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transmissao-notificacao")
public class TransmissaoDeNotificacaoController {
    @Autowired
    TransmissaoDeNotificacaoService service;

    @PostMapping
    public ResponseEntity<TransmissaoDeNotificacaoResponseDTO> create(@RequestBody @Valid TransmissaoDeNotificacaoRequestDTO data) {
        return ResponseEntity.ok(service.create(data));
    }

    @GetMapping
    public ResponseEntity<List<TransmissaoDeNotificacaoResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransmissaoDeNotificacaoResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
