package io.github.raphaelmuniz.uniflow.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CrudService<ReqDTO, ResDTO> {
    List<ResDTO> findAll();

    ResDTO findById(String id);

    @Transactional
    void delete(String id);
}
