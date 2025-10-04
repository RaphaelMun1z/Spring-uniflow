package io.github.raphaelmuniz.uniflow.dto.security;

import java.io.Serializable;
import java.util.Date;

public record TokenDTO(
        String username,
        Boolean authenticated,
        Date created,
        Date expiration,
        String accessToken,
        String refreshToken
) implements Serializable {
}
