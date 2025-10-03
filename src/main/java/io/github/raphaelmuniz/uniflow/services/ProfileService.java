package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.ProfileUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.ProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaModeloService;
import io.github.raphaelmuniz.uniflow.services.notificacao.NotificacaoService;
import io.github.raphaelmuniz.uniflow.services.usuario.AssinanteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProfileService {
    private final UsuarioRepository usuarioRepository;
    private final AssinanteService assinanteService;
    private final AssinaturaModeloService assinaturaModeloService;
    private final NotificacaoService notificacaoService;

    public ProfileService(
            UsuarioRepository usuarioRepository,
            AssinanteService assinanteService,
            AssinaturaModeloService assinaturaModeloService,
            NotificacaoService notificacaoService) {
        this.usuarioRepository = usuarioRepository;
        this.assinanteService = assinanteService;
        this.assinaturaModeloService = assinaturaModeloService;
        this.notificacaoService = notificacaoService;
    }

    public ProfileResponseDTO getAuthenticatedUserProfile(Usuario usuarioLogado) {
        AssinaturaProfileResponseDTO assinaturaDTO = null;
        try {
            assinaturaDTO = new AssinaturaProfileResponseDTO(assinanteService.obterAssinaturaVigenteEntidade(usuarioLogado.getId()));
            var modelo = assinaturaModeloService.findById(assinaturaDTO.getAssinaturaModelo().getId());
            assinaturaDTO.setAssinaturaModelo(modelo);
        } catch (BusinessException e) {
            System.out.println("Usuário " + usuarioLogado.getEmail() + " não possui assinatura ativa, o que é esperado para este perfil.");
        }

        int notificacoesNaoLidas = notificacaoService.countNotificacoesNaoLidas(usuarioLogado.getId());

        return new ProfileResponseDTO(usuarioLogado, assinaturaDTO, notificacoesNaoLidas);
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
        return getAuthenticatedUserProfile(usuarioSalvo);
    }

    @Transactional
    public void cancelarConta(Usuario usuarioLogado) {
        // Regra de Negócio: Antes de deletar, você pode adicionar lógicas complexas,
        // como anonimizar dados, verificar se o usuário é criador de grupos importantes, etc.
        // Por agora, faremos a exclusão direta.

        // A exclusão em cascata configurada nas entidades removerá dados dependentes.
        usuarioRepository.deleteById(usuarioLogado.getId());
    }
}
