package io.github.raphaelmuniz.uniflow.dto.res.profile;

import io.github.raphaelmuniz.uniflow.entities.AtividadeGrupo;
import io.github.raphaelmuniz.uniflow.entities.Grupo;
import io.github.raphaelmuniz.uniflow.entities.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

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
        this.criadorNome = inscricao.getGrupo().getCriador().getNome();
        this.criadorId = inscricao.getGrupo().getCriador().getId();
        this.dataIngresso = inscricao.getDataEntrada();
        this.papelNoGrupo = inscricao.getPapelNoGrupo();
    }
}
