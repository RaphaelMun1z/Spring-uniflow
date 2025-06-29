package io.github.raphaelmuniz.plansFy.services.interfaces;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CrudService<ReqDTO, ResDTO> {
    @Transactional
    ResDTO create(ReqDTO data);

    List<ResDTO> findAll();

    ResDTO findById(String id);

    @Transactional
    void delete(String id);
}
