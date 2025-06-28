package io.github.raphaelmuniz.plansFy.services.interfaces;

import java.util.List;

public interface Crud<ReqDTO, ResDTO> {
    ResDTO create(ReqDTO data);

    List<ResDTO> findAll();

    ResDTO findById(String id);

    void delete(String id);
}
