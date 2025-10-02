package io.github.raphaelmuniz.uniflow.services.validation;

@FunctionalInterface
public interface GrupoCreationRule {
    void validate(GrupoValidationContext context);
}