package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembroGrupoResponseDTO {
    private String idMembro;
    private String nome;
    private String email;
    private PapelGrupoEnum papelNoGrupo;

    public MembroGrupoResponseDTO(InscricaoGrupo inscricaoGrupo) {
        this.idMembro = inscricaoGrupo.getEstudanteMembro().getId();
        this.nome = inscricaoGrupo.getEstudanteMembro().getNome();
        this.email = inscricaoGrupo.getEstudanteMembro().getEmail();
        this.papelNoGrupo = inscricaoGrupo.getPapelNoGrupo();
    }
}
