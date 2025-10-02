package io.github.raphaelmuniz.uniflow.services.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PermissaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PermissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PermissaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService extends GenericCrudServiceImpl<PermissaoRequestDTO, PermissaoResponseDTO, Permissao, String> {
    protected PermissaoService(PermissaoRepository repository) {
        super(repository, PermissaoResponseDTO::new);
    }
}
