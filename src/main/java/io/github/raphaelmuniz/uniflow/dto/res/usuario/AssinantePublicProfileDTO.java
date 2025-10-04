package io.github.raphaelmuniz.uniflow.dto.res.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import lombok.Getter;

@Getter
public class AssinantePublicProfileDTO {
    private final String id;
    private final String nome;
    private final String tipo;
    private String areaAtuacao;

    public AssinantePublicProfileDTO(Assinante assinante) {
        this.id = assinante.getId();
        this.nome = assinante.getNome();

        if (assinante instanceof Professor professor) {
            this.tipo = "PROFESSOR";
            this.areaAtuacao = professor.getAreaAtuacao();
        } else {
            this.tipo = "ESTUDANTE";
        }
    }
}
