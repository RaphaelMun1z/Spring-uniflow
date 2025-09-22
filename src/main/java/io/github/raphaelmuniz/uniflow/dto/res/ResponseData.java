package io.github.raphaelmuniz.uniflow.dto.res;

public interface ResponseData<T> {
    T toModel();
}
