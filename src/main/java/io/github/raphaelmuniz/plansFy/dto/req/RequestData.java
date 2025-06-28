package io.github.raphaelmuniz.plansFy.dto.req;

public interface RequestData<T> {
    T toModel();
}
