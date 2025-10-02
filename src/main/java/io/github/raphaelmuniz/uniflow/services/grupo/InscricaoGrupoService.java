package io.github.raphaelmuniz.uniflow.services.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.InscricaoGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.InscricaoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.repositories.grupo.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class InscricaoGrupoService extends GenericCrudServiceImpl<InscricaoGrupoRequestDTO, InscricaoGrupoResponseDTO, InscricaoGrupo, String> {
    protected InscricaoGrupoService(InscricaoGrupoRepository repository) {
        super(repository, InscricaoGrupoResponseDTO::new);
    }
}
