package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssinaturaModeloService {

    private final AssinaturaModeloRepository repository;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    public AssinaturaModeloService(
            AssinaturaModeloRepository repository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository) {
        this.repository = repository;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<AssinaturaModeloResponseDTO> buscarTodos() {
        return repository.findAll().stream()
                .map(AssinaturaModeloResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AssinaturaModeloResponseDTO buscarPorId(String id) {
        AssinaturaModelo modelo = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Modelo de assinatura não encontrado com o ID: " + id));
        return new AssinaturaModeloResponseDTO(modelo);
    }

    @Transactional
    public AssinaturaModeloResponseDTO criar(AssinaturaModeloRequestDTO dto) {
        AssinaturaModelo novoModelo = new AssinaturaModelo();
        novoModelo.setNome(dto.nome());
        novoModelo.setDescricao(dto.descricao());
        novoModelo.setPreco(dto.preco());
        novoModelo.setDuracaoEmMeses(dto.duracaoEmMeses());
        novoModelo.setLimiteDeGrupos(dto.limiteDeGrupos());
        novoModelo.setLimiteMembrosPorGrupo(dto.limiteMembrosPorGrupo());
        novoModelo.setPermiteCriarSubgrupos(dto.permiteCriarSubgrupos());
        novoModelo.setPermiteTemplatesDeAtividade(dto.permiteTemplatesDeAtividade());

        AssinaturaModelo modeloSalvo = repository.save(novoModelo);
        return new AssinaturaModeloResponseDTO(modeloSalvo);
    }

    @Transactional
    public AssinaturaModeloResponseDTO atualizar(String id, AssinaturaModeloRequestDTO dto) {
        AssinaturaModelo modeloExistente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Modelo de assinatura não encontrado com o ID: " + id));

        modeloExistente.setNome(dto.nome());
        modeloExistente.setDescricao(dto.descricao());
        modeloExistente.setPreco(dto.preco());
        modeloExistente.setDuracaoEmMeses(dto.duracaoEmMeses());
        modeloExistente.setLimiteDeGrupos(dto.limiteDeGrupos());
        modeloExistente.setLimiteMembrosPorGrupo(dto.limiteMembrosPorGrupo());
        modeloExistente.setPermiteCriarSubgrupos(dto.permiteCriarSubgrupos());
        modeloExistente.setPermiteTemplatesDeAtividade(dto.permiteTemplatesDeAtividade());

        AssinaturaModelo modeloAtualizado = repository.save(modeloExistente);
        return new AssinaturaModeloResponseDTO(modeloAtualizado);
    }

    @Transactional
    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Modelo de assinatura não encontrado com o ID: " + id);
        }
        if (assinaturaUsuarioRepository.existsByAssinaturaModelo_Id(id)) {
            throw new BusinessException("Não é possível excluir um modelo de assinatura que está em uso por usuários.");
        }
        repository.deleteById(id);
    }
}