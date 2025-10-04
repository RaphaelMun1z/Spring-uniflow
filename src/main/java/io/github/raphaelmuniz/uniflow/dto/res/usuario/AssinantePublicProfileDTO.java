package io.github.raphaelmuniz.uniflow.dto.res.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;

public record AssinantePublicProfileDTO(
        String id,
        String nome,
        String tipo,
        String areaAtuacao
) {
    public static AssinantePublicProfileDTO fromEntity(Assinante assinante) {
        if (assinante == null) {
            return null;
        }

        if (assinante instanceof Professor professor) {
            return new AssinantePublicProfileDTO(
                    professor.getId(),
                    professor.getNome(),
                    "PROFESSOR",
                    professor.getAreaAtuacao()
            );
        }

        return new AssinantePublicProfileDTO(
                assinante.getId(),
                assinante.getNome(),
                "ESTUDANTE",
                null
        );
    }
}
