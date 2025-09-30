package io.github.raphaelmuniz.uniflow.dto.res;

import java.util.List;

public record PaginatedResponse<T>(List<T> content, int currentPage, int totalPages, long totalElements) {
}