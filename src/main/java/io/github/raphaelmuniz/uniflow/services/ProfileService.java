package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.ProfileUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.ProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaModeloService;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaUsuarioService;
import io.github.raphaelmuniz.uniflow.services.assinatura.PagamentoService;
import io.github.raphaelmuniz.uniflow.services.notificacao.NotificacaoAssinanteService;
import io.github.raphaelmuniz.uniflow.services.usuario.AssinanteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {

    private final UsuarioRepository usuarioRepository;
    private final AssinanteService assinanteService;
    private final AssinaturaUsuarioService assinaturaUsuarioService;
    private final PagamentoService pagamentoService;
    private final AssinaturaModeloService assinaturaModeloService;
    private final NotificacaoAssinanteService notificacaoAssinanteService;

    public ProfileService(
            UsuarioRepository usuarioRepository, AssinanteService assinanteService,
            AssinaturaUsuarioService assinaturaUsuarioService, PagamentoService pagamentoService,
            AssinaturaModeloService assinaturaModeloService, NotificacaoAssinanteService notificacaoAssinanteService) {
        this.usuarioRepository = usuarioRepository;
        this.assinanteService = assinanteService;
        this.assinaturaUsuarioService = assinaturaUsuarioService;
        this.pagamentoService = pagamentoService;
        this.assinaturaModeloService = assinaturaModeloService;
        this.notificacaoAssinanteService = notificacaoAssinanteService;
    }

    public ProfileResponseDTO buscarPerfilDoUsuarioAutenticado(Usuario usuarioLogado) {
        AssinaturaProfileResponseDTO assinaturaDTO = null;
        try {
            AssinaturaUsuario assinaturaEntidade = assinanteService.obterAssinaturaVigenteEntidade(usuarioLogado.getId());
            assinaturaDTO = AssinaturaProfileResponseDTO.fromEntity(assinaturaEntidade);
            var modelo = assinaturaModeloService.buscarPorId(assinaturaDTO.assinaturaModeloId());
            assinaturaDTO = assinaturaDTO.withModelo(modelo);
        } catch (BusinessException e) {
        }

        int notificacoesNaoLidas = notificacaoAssinanteService.countNotificacoesNaoLidas(usuarioLogado.getId());
        return ProfileResponseDTO.fromEntity(usuarioLogado, assinaturaDTO, notificacoesNaoLidas);
    }

    @Transactional
    public ProfileResponseDTO atualizarPerfilUsuario(Usuario usuarioLogado, ProfileUpdateRequestDTO dto) {
        if (dto.nome() != null && !dto.nome().isBlank()) {
            usuarioLogado.setNome(dto.nome());
        }
        if (usuarioLogado instanceof Professor professor && dto.areaAtuacao() != null) {
            professor.setAreaAtuacao(dto.areaAtuacao());
        }
        Usuario usuarioSalvo = usuarioRepository.save(usuarioLogado);
        return buscarPerfilDoUsuarioAutenticado(usuarioSalvo);
    }

    @Transactional
    public void cancelarConta(Usuario usuarioLogado) {
        usuarioRepository.deleteById(usuarioLogado.getId());
    }

    @Transactional(readOnly = true)
    public Page<AssinaturaProfileResponseDTO> buscarMinhasAssinaturas(String usuarioId, Pageable pageable) {
        Page<AssinaturaUsuario> paginaDeAssinaturas = assinaturaUsuarioService.buscarEntidadesPorAssinanteId(usuarioId, pageable);
        return paginaDeAssinaturas.map(AssinaturaProfileResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public Page<PagamentoResponseDTO> buscarMeusPagamentos(String usuarioId, Pageable pageable) {
        return pagamentoService.listarHistoricoDePagamentos(usuarioId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<GruposProfileResponseDTO> buscarMeusGrupos(String usuarioId, Pageable pageable) {
        return assinanteService.obterGruposPorAssinanteId(usuarioId, pageable);
    }
}