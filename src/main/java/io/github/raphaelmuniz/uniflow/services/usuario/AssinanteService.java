package io.github.raphaelmuniz.uniflow.services.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.AssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinantePublicProfileDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AssinanteService extends GenericCrudServiceImpl<AssinanteRequestDTO, AssinanteResponseDTO, Assinante, String> {
    private final UsuarioRepository usuarioRepository;
    private final InscricaoGrupoRepository inscricaoGrupoRepository;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    protected AssinanteService(
            UsuarioRepository usuarioRepository,
            AssinanteRepository assinanteRepository,
            InscricaoGrupoRepository inscricaoGrupoRepository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository
    ) {
        super(assinanteRepository, AssinanteResponseDTO::new);
        this.usuarioRepository = usuarioRepository;
        this.inscricaoGrupoRepository = inscricaoGrupoRepository;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
    }

    public Page<GruposProfileResponseDTO> obterGruposPorAssinanteId(String assinanteId, Pageable pageable) {
        Page<InscricaoGrupo> inscricoesPage = inscricaoGrupoRepository.findByMembro_Id(assinanteId, pageable);

        return inscricoesPage.map(GruposProfileResponseDTO::new);
    }

    public AssinaturaUsuario obterAssinaturaVigenteEntidade(String assinanteId) {
        return assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(assinanteId,
                        StatusAssinaturaUsuarioEnum.getStatusVigentes(),
                        LocalDateTime.now())
                .orElseThrow(() -> new BusinessException("É necessário um plano de assinatura ativo."));
    }

    public boolean verificarSePossuiAssinaturaAtiva(String assinanteId) {
        return assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(assinanteId,
                        StatusAssinaturaUsuarioEnum.getStatusVigentes(),
                        LocalDateTime.now())
                .isPresent();
    }

    @Transactional(readOnly = true)
    public AssinantePublicProfileDTO buscarPerfilPublicoPorId(String id) {
        Assinante assinante = (Assinante) usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado."));
        return new AssinantePublicProfileDTO(assinante);
    }

    @Transactional(readOnly = true)
    public Page<AssinantePublicProfileDTO> listarTodosPerfisPublicos(Pageable pageable) {
        Page<Assinante> assinantesPage = usuarioRepository.findAllAssinantes(pageable);
        return assinantesPage.map(AssinantePublicProfileDTO::new);
    }
}
