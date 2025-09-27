package io.github.raphaelmuniz.uniflow.dto.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class AccountCredentialsDTO implements Serializable {
    private String username;
    private String password;
}
