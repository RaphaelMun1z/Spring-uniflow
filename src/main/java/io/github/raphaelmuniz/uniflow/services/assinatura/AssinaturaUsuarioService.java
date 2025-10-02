package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssinaturaUsuarioService extends GenericCrudServiceImpl<AssinaturaUsuarioRequestDTO, AssinaturaUsuarioResponseDTO, AssinaturaUsuario, String> {
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;
    private final AssinaturaModeloRepository assinaturaModeloRepository;
    private final AssinanteRepository assinanteRepository;

    protected AssinaturaUsuarioService(
            AssinaturaUsuarioRepository repository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            AssinaturaModeloRepository assinaturaModeloRepository,
            AssinanteRepository assinanteRepository) {
        super(repository, AssinaturaUsuarioResponseDTO::new);
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.assinaturaModeloRepository = assinaturaModeloRepository;
        this.assinanteRepository = assinanteRepository;
    }

    public AssinaturaUsuarioResponseDTO create(AssinaturaUsuarioRequestDTO data) {
        Assinante assinante = assinanteRepository.findById(data.getAssinanteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado"));
        AssinaturaModelo assinaturaModelo = assinaturaModeloRepository.findById(data.getAssinaturaModeloId()).orElseThrow(() -> new NotFoundException("Assinatura modelo não encontrada"));

        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, null, LocalDateTime.now().plusMonths(1), StatusAssinaturaUsuarioEnum.ATIVA, assinaturaModelo, assinante, null);
        AssinaturaUsuario saved = repository.save(assinaturaUsuario);
        return new AssinaturaUsuarioResponseDTO(saved);
    }

    public Page<AssinaturaProfileResponseDTO> findByAssinanteId(String assinanteId, Pageable pageable) {
        Page<AssinaturaUsuario> assinaturasPage = assinaturaUsuarioRepository.findByAssinante_Id(assinanteId, pageable);
        return assinaturasPage.map(AssinaturaProfileResponseDTO::new);
    }
}
