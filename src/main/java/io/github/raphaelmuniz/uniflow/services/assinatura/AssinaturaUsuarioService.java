package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
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
import io.github.raphaelmuniz.uniflow.services.regras.assinante.config.ContextoValidacaoAssinatura;
import io.github.raphaelmuniz.uniflow.services.regras.assinante.config.RegraValidacaoAssinatura;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AssinaturaUsuarioService extends GenericCrudServiceImpl<AssinaturaUsuarioRequestDTO, AssinaturaUsuarioResponseDTO, AssinaturaUsuario, String> {
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;
    private final AssinaturaModeloRepository assinaturaModeloRepository;
    private final AssinanteRepository assinanteRepository;
    private final List<RegraValidacaoAssinatura> regrasCriacaoAssinatura;

    protected AssinaturaUsuarioService(
            AssinaturaUsuarioRepository repository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            AssinaturaModeloRepository assinaturaModeloRepository,
            AssinanteRepository assinanteRepository,
            @Qualifier("regrasCriacaoAssinatura") List<RegraValidacaoAssinatura> regras) {
        super(repository, AssinaturaUsuarioRequestDTO::toModel, AssinaturaUsuarioResponseDTO::new);
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.assinaturaModeloRepository = assinaturaModeloRepository;
        this.assinanteRepository = assinanteRepository;
        this.regrasCriacaoAssinatura = regras;
    }

    @Override
    public AssinaturaUsuarioResponseDTO create(AssinaturaUsuarioRequestDTO data) {
        Assinante assinante = assinanteRepository.findById(data.getAssinanteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado"));
        AssinaturaModelo assinaturaModelo = assinaturaModeloRepository.findById(data.getAssinaturaModeloId()).orElseThrow(() -> new NotFoundException("Assinatura modelo não encontrada"));

        ContextoValidacaoAssinatura contexto = new ContextoValidacaoAssinatura(assinante, assinaturaModelo);
        regrasCriacaoAssinatura.forEach(regra -> regra.verificar(contexto));

        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, null, LocalDateTime.now().plusMonths(1), StatusAssinaturaUsuarioEnum.ATIVA, assinaturaModelo, assinante, null);
        AssinaturaUsuario saved = repository.save(assinaturaUsuario);
        return new AssinaturaUsuarioResponseDTO(saved);
    }

    public List<AssinaturaProfileResponseDTO> findByAssinanteId(String assinanteId) {
        List<AssinaturaUsuario> assinaturaUsuario = assinaturaUsuarioRepository.findByAssinanteId(assinanteId);
        return assinaturaUsuario.stream().map(au -> {
            AssinaturaModeloResponseDTO assinaturaModeloResponseDTO = new AssinaturaModeloResponseDTO(au.getAssinaturaModelo());
            return new AssinaturaProfileResponseDTO(null, au.getDataInicio(), au.getDataExpiracao(), au.getStatus(), assinaturaModeloResponseDTO);
        }).toList();
    }
}
