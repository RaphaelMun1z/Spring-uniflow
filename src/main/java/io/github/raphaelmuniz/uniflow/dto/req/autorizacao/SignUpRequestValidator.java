package io.github.raphaelmuniz.uniflow.dto.req.autorizacao;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SignUpRequestValidator implements ConstraintValidator<ValidSignUpRequest, SignUpRequestDTO> {

    @Override
    public boolean isValid(SignUpRequestDTO dto, ConstraintValidatorContext context) {
        if (dto == null || dto.tipo() == null) {
            return true;
        }

        if (dto.tipo() == SignUpRequestDTO.TipoUsuario.ESTUDANTE) {
            if (dto.periodo() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("O campo 'periodo' é obrigatório para o tipo ESTUDANTE.")
                        .addPropertyNode("periodo").addConstraintViolation();
                return false;
            }
        } else if (dto.tipo() == SignUpRequestDTO.TipoUsuario.PROFESSOR) {
            if (dto.areaAtuacao() == null || dto.areaAtuacao().isBlank()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("O campo 'areaAtuacao' é obrigatório para o tipo PROFESSOR.")
                        .addPropertyNode("areaAtuacao").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}