package io.github.raphaelmuniz.uniflow.controllers.interfaces;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface CrudController<ReqDTO, ResDTO> {
    @GetMapping
    ResponseEntity<List<ResDTO>> findAll();

    @GetMapping("/{id}")
    ResponseEntity<ResDTO> findById(@PathVariable String id);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id);
}
