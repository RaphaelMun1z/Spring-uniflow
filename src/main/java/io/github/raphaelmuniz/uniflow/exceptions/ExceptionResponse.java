package io.github.raphaelmuniz.uniflow.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private String localDateTime;
    private List<String> message;
    private String details;
}

