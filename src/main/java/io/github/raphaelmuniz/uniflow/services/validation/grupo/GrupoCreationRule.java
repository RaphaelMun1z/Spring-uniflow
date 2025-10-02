package io.github.raphaelmuniz.uniflow.services.validation.grupo;

@FunctionalInterface
public interface GrupoCreationRule {
    void validate(GrupoValidationContext context);
}