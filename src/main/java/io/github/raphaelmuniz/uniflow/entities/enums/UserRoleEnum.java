package io.github.raphaelmuniz.uniflow.entities.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {
    ADMIN("ADMIN"),
    ESTUDANTE("ESTUDANTE"),
    PROFESSOR("PROFESSOR");

    private final String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

}
