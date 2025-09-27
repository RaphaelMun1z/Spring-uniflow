package io.github.raphaelmuniz.uniflow.dto.security;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class TokenDTO implements Serializable {
    private String username;
    private Boolean authenticated;
    private Date created;
    private Date expiration;
    private String acessToken;
    private String refreshToken;
}
