package io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.listarAtividadesStrategy;

import io.github.raphaelmuniz.uniflow.dto.res.grupo.AtividadeDoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;

import java.util.List;

public interface ListarAtividadesStrategy {
    List<AtividadeDoGrupoResponseDTO> listarAtividades(Grupo grupo, Usuario usuarioLogado);

    TipoGrupoEnum supports();
}
