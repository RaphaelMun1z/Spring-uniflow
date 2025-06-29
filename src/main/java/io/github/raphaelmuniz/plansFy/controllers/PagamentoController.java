package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    @Autowired
    PagamentoService service;

    @PostMapping
    public ResponseEntity<PagamentoResponseDTO> create(@RequestBody @Valid PagamentoRequestDTO data) {
        return ResponseEntity.ok(service.create(data));
    }
}
