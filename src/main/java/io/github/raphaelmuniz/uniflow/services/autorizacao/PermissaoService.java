package io.github.raphaelmuniz.uniflow.services.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PermissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PermissaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissaoService {

    private final PermissaoRepository permissaoRepository;

    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = permissaoRepository;
    }

    @Transactional(readOnly = true)
    public List<PermissaoResponseDTO> buscarTodas() {
        return permissaoRepository.findAll().stream()
                .map(PermissaoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PermissaoResponseDTO buscarPorId(String id) {
        Permissao permissao = permissaoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permissão não encontrada com o ID: " + id));
        return new PermissaoResponseDTO(permissao);
    }
}