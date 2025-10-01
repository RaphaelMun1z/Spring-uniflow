package io.github.raphaelmuniz.uniflow.exceptions;

import io.github.raphaelmuniz.uniflow.exceptions.models.BadCredentialsException;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.InvalidJwtAuthenticationException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.*;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ExceptionResponse> notFoundException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), List.of(ex.getMessage()), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public final ResponseEntity<ExceptionResponse> businessException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), List.of(ex.getMessage()), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ExceptionResponse> illegalArgumentException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), List.of(ex.getMessage()), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<ExceptionResponse> dataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String mensagemAmigavel = "Ocorreu um erro de integridade dos dados. Verifique os campos informados.";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Throwable causaRaiz = ex.getMostSpecificCause();
        String mensagemCausa = causaRaiz.getMessage().toLowerCase();

        if (mensagemCausa.contains("unique constraint") ||
                mensagemCausa.contains("duplicate entry") ||
                mensagemCausa.contains("viola a restrição de unicidade")) {

            mensagemAmigavel = "Recurso já existe.";
            status = HttpStatus.CONFLICT;
        }

        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), List.of(mensagemAmigavel), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<ExceptionResponse> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> mensagens = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), mensagens, request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<ExceptionResponse> invalidJwtAuthenticationException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), List.of(ex.getMessage()), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<ExceptionResponse> badCredentialsException(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), List.of(ex.getMessage()), request.getDescription(false));
        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception ex, WebRequest request) {
        logger.error("Ocorreu uma exceção não tratada", ex);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                List.of("Ocorreu um erro inesperado no servidor. Por favor, tente novamente mais tarde."),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
