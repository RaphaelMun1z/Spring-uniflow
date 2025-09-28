package io.github.raphaelmuniz.uniflow.entities.enums;

public enum UserRoleEnum {
    ADMIN("ADMIN"),
    ESTUDANTE("ESTUDANTE"),
    PROFESSOR("PROFESSOR");

    private String role;

    UserRoleEnum(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
