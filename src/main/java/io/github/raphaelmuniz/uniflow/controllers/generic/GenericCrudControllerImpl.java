package io.github.raphaelmuniz.uniflow.controllers.generic;

import io.github.raphaelmuniz.uniflow.controllers.interfaces.CrudController;
import io.github.raphaelmuniz.uniflow.services.interfaces.CrudService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GenericCrudControllerImpl<ReqDTO, ResDTO> implements CrudController<ReqDTO, ResDTO> {
    protected final CrudService<ReqDTO, ResDTO> service;

    protected GenericCrudControllerImpl(CrudService<ReqDTO, ResDTO> service) {
        this.service = service;
    }

    public ResponseEntity<List<ResDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    public ResponseEntity<ResDTO> findById(String id) {
        return ResponseEntity.ok(service.findById(id));
    }

    public ResponseEntity<Void> delete(String id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
