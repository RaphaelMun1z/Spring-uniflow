package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssinaturaModeloService extends GenericCrudServiceImpl<AssinaturaModeloRequestDTO, AssinaturaModeloResponseDTO, AssinaturaModelo, String> {
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    protected AssinaturaModeloService(
            AssinaturaModeloRepository repository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository
    ) {
        super(repository, AssinaturaModeloResponseDTO::new);
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
    }

    @Transactional
    public AssinaturaModeloResponseDTO criar(AssinaturaModeloRequestDTO dto) {
        AssinaturaModelo novoModelo = new AssinaturaModelo();
        novoModelo.setNome(dto.getNome());
        novoModelo.setDescricao(dto.getDescricao());
        novoModelo.setPreco(dto.getPreco());
        novoModelo.setLimiteDeGrupos(dto.getLimiteDeGrupos());
        novoModelo.setLimiteMembrosPorGrupo(dto.getLimiteMembrosPorGrupo());
        novoModelo.setPermiteCriarSubgrupos(dto.getPermiteCriarSubgrupos());
        novoModelo.setPermiteTemplatesDeAtividade(dto.getPermiteTemplatesDeAtividade());

        AssinaturaModelo modeloSalvo = repository.save(novoModelo);

        return toResponseMapper.apply(modeloSalvo);
    }

    @Transactional
    public AssinaturaModeloResponseDTO atualizar(String id, AssinaturaModeloRequestDTO dto) {
        AssinaturaModelo modeloExistente = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Modelo de assinatura não encontrado com o ID: " + id));

        modeloExistente.setNome(dto.getNome());
        modeloExistente.setDescricao(dto.getDescricao());
        modeloExistente.setPreco(dto.getPreco());
        modeloExistente.setLimiteDeGrupos(dto.getLimiteDeGrupos());
        modeloExistente.setLimiteMembrosPorGrupo(dto.getLimiteMembrosPorGrupo());
        modeloExistente.setPermiteCriarSubgrupos(dto.getPermiteCriarSubgrupos());
        modeloExistente.setPermiteTemplatesDeAtividade(dto.getPermiteTemplatesDeAtividade());

        AssinaturaModelo modeloAtualizado = repository.save(modeloExistente);

        return toResponseMapper.apply(modeloAtualizado);
    }

    @Transactional
    public void deletar(String id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Modelo de assinatura não encontrado com o ID: " + id);
        }

        boolean isEmUso = assinaturaUsuarioRepository.existsByAssinaturaModelo_Id(id);
        if (isEmUso) {
            throw new BusinessException("Não é possível excluir um modelo de assinatura que está em uso por usuários.");
        }

        repository.deleteById(id);
    }
}
