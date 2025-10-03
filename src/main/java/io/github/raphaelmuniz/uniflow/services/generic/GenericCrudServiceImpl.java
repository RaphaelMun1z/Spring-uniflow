package io.github.raphaelmuniz.uniflow.services.generic;

import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.services.interfaces.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class GenericCrudServiceImpl<ReqDTO, ResDTO, E, ID extends Serializable> implements CrudService<ReqDTO, ResDTO> {
    protected final JpaRepository<E, ID> repository;
    protected final Function<E, ResDTO> toResponseMapper;

    protected GenericCrudServiceImpl(
            JpaRepository<E, ID> repository,
            Function<E, ResDTO> toResponseMapper) {
        this.repository = repository;
        this.toResponseMapper = toResponseMapper;
    }

    public List<ResDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(toResponseMapper)
                .collect(Collectors.toList());
    }

    public ResDTO findById(ID id) {
        E entity = repository.findById((ID) id)
                .orElseThrow(() -> new NotFoundException("Entidade não encontrada."));
        return toResponseMapper.apply(entity);
    }
}
