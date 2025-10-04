package io.github.raphaelmuniz.uniflow.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.raphaelmuniz.uniflow.exceptions.ExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                LocalDateTime.now(),
                List.of(authException.getMessage()),
                request.getRequestURI()
        );

        new ObjectMapper().writeValue(response.getWriter(), exceptionResponse);
    }
}
