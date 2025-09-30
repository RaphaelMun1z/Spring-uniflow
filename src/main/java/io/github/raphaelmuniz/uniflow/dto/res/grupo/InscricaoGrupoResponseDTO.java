package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InscricaoGrupoResponseDTO {
    private String id;
    private String alunoId;
    private String grupoId;
    private PapelGrupoEnum papelNoGrupo;

    public InscricaoGrupoResponseDTO(InscricaoGrupo inscricaoGrupo){
        this.id = inscricaoGrupo.getId();
        this.alunoId = inscricaoGrupo.getEstudanteMembro().getId();
        this.grupoId = inscricaoGrupo.getGrupo().getId();
        this.papelNoGrupo = inscricaoGrupo.getPapelNoGrupo();
    }
}
