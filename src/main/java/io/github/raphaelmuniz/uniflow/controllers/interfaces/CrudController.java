package io.github.raphaelmuniz.uniflow.controllers.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudController<ReqDTO, ResDTO> {
    @PostMapping
    public ResponseEntity<ResDTO> create(@RequestBody @Valid ReqDTO data);

    @GetMapping
    public ResponseEntity<List<ResDTO>> findAll();

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO> findById(@PathVariable String id);

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id);
}
