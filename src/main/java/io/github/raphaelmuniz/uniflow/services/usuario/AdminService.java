package io.github.raphaelmuniz.uniflow.services.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PermissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinantePublicProfileDTO;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaModeloService;
import io.github.raphaelmuniz.uniflow.services.assinatura.AssinaturaUsuarioService;
import io.github.raphaelmuniz.uniflow.services.autorizacao.PapelService;
import io.github.raphaelmuniz.uniflow.services.autorizacao.PermissaoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AssinanteService assinanteService;
    private final AssinaturaModeloService assinaturaModeloService;
    private final PapelService papelService;
    private final PermissaoService permissaoService;
    private final AssinaturaUsuarioService assinaturaUsuarioService;

    public AdminService(
            AssinanteService assinanteService,
            AssinaturaModeloService assinaturaModeloService,
            PapelService papelService,
            PermissaoService permissaoService,
            AssinaturaUsuarioService assinaturaUsuarioService) {
        this.assinanteService = assinanteService;
        this.assinaturaModeloService = assinaturaModeloService;
        this.papelService = papelService;
        this.permissaoService = permissaoService;
        this.assinaturaUsuarioService = assinaturaUsuarioService;
    }

    public Page<AssinantePublicProfileDTO> listarUsuarios(String tipo, Pageable pageable) {
        return assinanteService.listarTodosPerfisPublicos(tipo, pageable);
    }

    public List<AssinaturaModeloResponseDTO> listarPlanos() {
        return assinaturaModeloService.buscarTodos();
    }

    public AssinaturaModeloResponseDTO criarPlano(AssinaturaModeloRequestDTO dto) {
        return assinaturaModeloService.criar(dto);
    }

    public AssinaturaModeloResponseDTO atualizarPlano(String id, AssinaturaModeloRequestDTO dto) {
        return assinaturaModeloService.atualizar(id, dto);
    }

    public void deletarPlano(String id) {
        assinaturaModeloService.deletar(id);
    }

    public List<PapelResponseDTO> listarPapeis() {
        return papelService.buscarTodos();
    }

    public PapelResponseDTO criarPapel(PapelRequestDTO dto) {
        return papelService.criar(dto);
    }

    public List<PermissaoResponseDTO> listarPermissoes() {
        return permissaoService.buscarTodas();
    }

    public Page<AssinaturaUsuarioResponseDTO> listarAssinaturasDeUsuarios(Pageable pageable) {
        return assinaturaUsuarioService.buscarTodas(pageable);
    }

    public AssinaturaUsuarioResponseDTO criarAssinaturaParaUsuario(AssinaturaUsuarioRequestDTO dto) {
        return assinaturaUsuarioService.criarAssinatura(dto);
    }

    public AssinaturaUsuarioResponseDTO cancelarAssinaturaDeUsuario(String id) {
        return assinaturaUsuarioService.cancelar(id);
    }
}
