package io.github.raphaelmuniz.uniflow.dto.res.profile;

import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GruposProfileResponseDTO {
    private String grupoId;
    private String descricao;
    private String titulo;
    private String criadorNome;
    private String criadorId;
    private LocalDateTime dataIngresso;
    private PapelGrupoEnum papelNoGrupo;

    public GruposProfileResponseDTO(InscricaoGrupo inscricao) {
        this.grupoId = inscricao.getGrupo().getId();
        this.descricao = inscricao.getGrupo().getDescricao();
        this.titulo = inscricao.getGrupo().getTitulo();
        this.criadorNome = inscricao.getGrupo().getAssinanteCriadorGrupo().getNome();
        this.criadorId = inscricao.getGrupo().getAssinanteCriadorGrupo().getId();
        this.dataIngresso = inscricao.getDataEntrada();
        this.papelNoGrupo = inscricao.getPapelNoGrupo();
    }
}
