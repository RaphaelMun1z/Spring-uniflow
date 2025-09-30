package io.github.raphaelmuniz.uniflow.dto.req.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.RequestData;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InscricaoGrupoRequestDTO implements RequestData<InscricaoGrupo> {
    @NotNull
    private String alunoId;

    @NotNull
    private String grupoId;

    @NotNull
    private PapelGrupoEnum papelNoGrupo;

    public InscricaoGrupo toModel() {
        return new InscricaoGrupo(null, null, papelNoGrupo, null, null);
    }
}
