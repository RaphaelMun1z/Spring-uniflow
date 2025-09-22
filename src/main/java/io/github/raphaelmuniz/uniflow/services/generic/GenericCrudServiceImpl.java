package io.github.raphaelmuniz.uniflow.services.generic;

import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.services.interfaces.CrudService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class GenericCrudServiceImpl<ReqDTO, ResDTO, E, ID extends Serializable> implements CrudService<ReqDTO, ResDTO> {
    protected final JpaRepository<E, ID> repository;
    protected final Function<ReqDTO, E> toEntityMapper;
    protected final Function<E, ResDTO> toResponseMapper;

    protected GenericCrudServiceImpl(
            JpaRepository<E, ID> repository,
            Function<ReqDTO, E> toEntityMapper,
            Function<E, ResDTO> toResponseMapper) {
        this.repository = repository;
        this.toEntityMapper = toEntityMapper;
        this.toResponseMapper = toResponseMapper;
    }

    @Transactional
    public ResDTO create(ReqDTO data) {
        E entity = toEntityMapper.apply(data);
        E saved = repository.save(entity);
        return toResponseMapper.apply(saved);
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

    @Transactional
    public void delete(ID id) {
        if (repository.findById((ID) id).isEmpty()) {
            throw new NotFoundException("Entidade não encontrada.");
        }
        repository.deleteById((ID) id);
    }
}
