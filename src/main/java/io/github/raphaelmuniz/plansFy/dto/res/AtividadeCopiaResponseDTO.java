package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.Grupo;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AtividadeCopiaResponseDTO {
    private String id;
    private StatusEntregaEnum status;
    private Grupo grupo;
    private Assinante assinante;

    public AtividadeCopiaResponseDTO(AtividadeCopia atividadeCopia) {
        this.id = atividadeCopia.getId();
        this.status = atividadeCopia.getStatus();
        this.grupo = atividadeCopia.getGrupo();
        this.assinante = atividadeCopia.getAssinante();
    }

    public AtividadeCopia toModel() {
        return new AtividadeCopia(id, status, grupo, assinante);
    }
}
