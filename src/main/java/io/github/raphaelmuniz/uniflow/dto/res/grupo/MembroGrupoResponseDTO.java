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
        this.idMembro = inscricaoGrupo.getMembro().getId();
        this.nome = inscricaoGrupo.getMembro().getNome();
        this.email = inscricaoGrupo.getMembro().getEmail();
        this.papelNoGrupo = inscricaoGrupo.getPapelNoGrupo();
    }
}
