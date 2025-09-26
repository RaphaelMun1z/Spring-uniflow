package io.github.raphaelmuniz.uniflow.dto.res;

import lombok.Getter;
import java.util.List;

@Getter
public class PaginatedResponse<T> {
    private final List<T> content;
    private final int currentPage;
    private final int totalPages;
    private final long totalElements;

    public PaginatedResponse(List<T> content, int currentPage, int totalPages, long totalElements) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}