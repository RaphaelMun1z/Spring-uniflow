package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.interfaces.CrudController;
import io.github.raphaelmuniz.plansFy.services.interfaces.CrudService;
import org.hibernate.service.Service;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class GenericCrudControllerImpl<ReqDTO, ResDTO> implements CrudController<ReqDTO, ResDTO> {
    protected final CrudService<ReqDTO, ResDTO> service;

    protected GenericCrudControllerImpl(CrudService<ReqDTO, ResDTO> service) {
        this.service = service;
    }

    public ResponseEntity<ResDTO> create(ReqDTO data) {
        return ResponseEntity.ok(service.create(data));
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
