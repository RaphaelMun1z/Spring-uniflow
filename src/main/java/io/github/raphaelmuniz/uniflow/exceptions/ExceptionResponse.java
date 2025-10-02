package io.github.raphaelmuniz.uniflow.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private LocalDateTime localDateTime;
    private List<String> message;
    private String details;
}
