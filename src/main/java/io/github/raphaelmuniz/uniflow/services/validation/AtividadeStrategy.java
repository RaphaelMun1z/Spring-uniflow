package io.github.raphaelmuniz.uniflow.services.validation;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.AdicionarAtividadeRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;

public interface AtividadeStrategy {
    void adicionarAtividade(Grupo grupo, AdicionarAtividadeRequestDTO dto, Usuario usuarioLogado);

    TipoGrupoEnum supports();
}