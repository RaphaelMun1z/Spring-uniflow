package io.github.raphaelmuniz.plansFy.dto.res;

public interface ResponseData<T> {
    T toModel();
}
