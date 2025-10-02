package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import lombok.*;

@Getter
@Setter
public class MembroGrupoResponseDTO {
    private String id;
    private String nome;
    private PapelGrupoEnum papelNoGrupo;

    public MembroGrupoResponseDTO(InscricaoGrupo inscricao) {
        this.id = inscricao.getMembro().getId();
        this.nome = inscricao.getMembro().getNome();
        this.papelNoGrupo = inscricao.getPapelNoGrupo();
    }
}
