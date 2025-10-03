package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AssinaturaUsuarioService{
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final AssinaturaModeloRepository assinaturaModeloRepository;

    public AssinaturaUsuarioService(
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            UsuarioRepository usuarioRepository,
            AssinaturaModeloRepository assinaturaModeloRepository) {
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.assinaturaModeloRepository = assinaturaModeloRepository;
    }

    @Transactional
    public AssinaturaUsuarioResponseDTO criarAssinatura(AssinaturaUsuarioRequestDTO dto) {
        assinaturaUsuarioRepository.findFirstVigenteByAssinanteId(
                dto.getUsuarioId(), StatusAssinaturaUsuarioEnum.getStatusVigentes(), LocalDateTime.now()
        ).ifPresent(s -> {
            throw new BusinessException("Este usuário já possui uma assinatura ativa.");
        });

        Assinante assinante = (Assinante) usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com o ID: " + dto.getUsuarioId()));

        AssinaturaModelo modelo = assinaturaModeloRepository.findById(dto.getModeloId())
                .orElseThrow(() -> new NotFoundException("Modelo de assinatura não encontrado com o ID: " + dto.getModeloId()));

        AssinaturaUsuario novaAssinatura = new AssinaturaUsuario();
        novaAssinatura.setAssinante(assinante);
        novaAssinatura.setAssinaturaModelo(modelo);
        novaAssinatura.setStatus(StatusAssinaturaUsuarioEnum.ATIVA);
        novaAssinatura.setDataInicio(LocalDateTime.now());

        long duracaoEmMeses = modelo.getDuracaoEmMeses();
        novaAssinatura.setDataExpiracao(LocalDateTime.now().plusMonths(duracaoEmMeses));

        AssinaturaUsuario assinaturaSalva = assinaturaUsuarioRepository.save(novaAssinatura);
        return new AssinaturaUsuarioResponseDTO(assinaturaSalva);
    }

    @Transactional(readOnly = true)
    public Page<AssinaturaUsuarioResponseDTO> buscarTodas(Pageable pageable) {
        Page<AssinaturaUsuario> page = assinaturaUsuarioRepository.findAll(pageable);
        return page.map(AssinaturaUsuarioResponseDTO::new);
    }

    @Transactional(readOnly = true)
    public AssinaturaUsuarioResponseDTO buscarPorId(String id) {
        AssinaturaUsuario assinatura = assinaturaUsuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assinatura não encontrada com o ID: " + id));
        return new AssinaturaUsuarioResponseDTO(assinatura);
    }

    @Transactional
    public AssinaturaUsuarioResponseDTO cancelar(String id) {
        AssinaturaUsuario assinatura = assinaturaUsuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Assinatura não encontrada com o ID: " + id));

        if (assinatura.getStatus() != StatusAssinaturaUsuarioEnum.ATIVA) {
            throw new BusinessException("Apenas assinaturas com status 'ATIVA' podem ser canceladas.");
        }

        assinatura.setStatus(StatusAssinaturaUsuarioEnum.CANCELADA);
        assinatura.setDataExpiracao(LocalDateTime.now());

        AssinaturaUsuario assinaturaCancelada = assinaturaUsuarioRepository.save(assinatura);
        return new AssinaturaUsuarioResponseDTO(assinaturaCancelada);
    }

    public Page<AssinaturaUsuarioResponseDTO> findByAssinanteId(String assinanteId, Pageable pageable) {
        Page<AssinaturaUsuario> assinaturasPage = assinaturaUsuarioRepository.findByAssinante_Id(assinanteId, pageable);
        return assinaturasPage.map(AssinaturaUsuarioResponseDTO::new);
    }
}
