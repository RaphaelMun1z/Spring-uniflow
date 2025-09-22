package io.github.raphaelmuniz.uniflow.dto.req;

public interface RequestData<T> {
    T toModel();
}
