package io.github.raphaelmuniz.plansFy.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class ExceptionResponse {
    private Date timestamp;
    private List<String> message;
    private String details;
}
