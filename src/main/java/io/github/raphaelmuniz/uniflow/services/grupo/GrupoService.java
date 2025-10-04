package io.github.raphaelmuniz.uniflow.services.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.AdicionarAtividadeRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.GrupoUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.SubGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.AtividadeDoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.MembroGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.atividadeStrategy.AtividadeStrategy;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.atividadeStrategy.provider.AtividadeStrategyProvider;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.GrupoFactory;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.listarAtividadesStrategy.ListarAtividadesStrategy;
import io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.listarAtividadesStrategy.provider.ListarAtividadesStrategyProvider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GrupoService {
    private final GrupoRepository grupoRepository;
    private final GrupoFactory grupoFactory;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final InscricaoGrupoRepository inscricaoGrupoRepository;

    private final AtividadeStrategyProvider atividadeStrategyProvider;
    private final ListarAtividadesStrategyProvider listarAtividadesStrategyProvider;

    public GrupoService(
            GrupoRepository repository,
            GrupoFactory grupoFactory,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            UsuarioRepository usuarioRepository,
            InscricaoGrupoRepository inscricaoGrupoRepository,
            AtividadeStrategyProvider atividadeStrategyProvider,
            ListarAtividadesStrategyProvider listarAtividadesStrategyProvider) {
        this.grupoRepository = repository;
        this.grupoFactory = grupoFactory;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.usuarioRepository = usuarioRepository;
        this.inscricaoGrupoRepository = inscricaoGrupoRepository;
        this.atividadeStrategyProvider = atividadeStrategyProvider;
        this.listarAtividadesStrategyProvider = listarAtividadesStrategyProvider;
    }

    @Transactional
    public GrupoResponseDTO criarGrupo(GrupoRequestDTO data, Usuario usuarioAutenticado) {
        Grupo novoGrupo = grupoFactory.criarGrupo(data, usuarioAutenticado);
        Grupo grupoSalvo = grupoRepository.save(novoGrupo);
        return GrupoResponseDTO.fromEntity(grupoSalvo);
    }

    @Transactional
    public void juntarComConvite(String grupoId, String codigoConvite, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        if (grupo.getTipoGrupo() != TipoGrupoEnum.TURMA) {
            throw new BusinessException("Apenas é possível entrar com código de convite em grupos do tipo TURMA.");
        }

        if (!codigoConvite.equals(grupo.getCodigoConvite())) {
            throw new BusinessException("Código de convite inválido.");
        }

        boolean jaInscrito = grupo.getInscricoes().stream()
                .anyMatch(i -> i.getMembro().getId().equals(usuarioLogado.getId()));
        if (jaInscrito) {
            throw new BusinessException("Você já está inscrito neste grupo.");
        }

        verificarLimiteDeMembros(grupo);

        InscricaoGrupo novaInscricao = new InscricaoGrupo();
        novaInscricao.setMembro((Assinante) usuarioLogado);
        novaInscricao.setPapelNoGrupo(PapelGrupoEnum.MEMBRO);

        grupo.addInscricao(novaInscricao);
        grupoRepository.save(grupo);
    }

    @Transactional
    public void adicionarMembroDiretamente(String grupoId, String usuarioParaAdicionarId, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        if (!grupo.getAssinanteCriadorGrupo().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Apenas o criador do grupo pode adicionar membros diretamente.");
        }

        if (grupo.getTipoGrupo() != TipoGrupoEnum.GRUPO_ESTUDO) {
            throw new BusinessException("A adição direta de membros só é permitida em GRUPO_ESTUDO.");
        }

        verificarLimiteDeMembros(grupo);

        Assinante novoMembro = (Assinante) usuarioRepository.findById(usuarioParaAdicionarId)
                .orElseThrow(() -> new NotFoundException("Usuário a ser adicionado não encontrado."));

        boolean jaInscrito = grupo.getInscricoes().stream()
                .anyMatch(i -> i.getMembro().getId().equals(novoMembro.getId()));
        if (jaInscrito) {
            throw new BusinessException("Este usuário já está no grupo.");
        }

        InscricaoGrupo novaInscricao = new InscricaoGrupo();
        novaInscricao.setMembro(novoMembro);
        novaInscricao.setPapelNoGrupo(PapelGrupoEnum.MEMBRO);

        grupo.addInscricao(novaInscricao);
        grupoRepository.save(grupo);
    }

    @Transactional
    public void alterarPapelMembro(String grupoId, String membroId, PapelGrupoEnum novoPapel, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        if (!grupo.getAssinanteCriadorGrupo().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Apenas o criador do grupo pode alterar papéis de membros.");
        }

        if (grupo.getTipoGrupo() != TipoGrupoEnum.TURMA) {
            throw new BusinessException("A alteração de papéis só é permitida em grupos do tipo TURMA.");
        }

        InscricaoGrupo inscricao = inscricaoGrupoRepository.findByGrupo_IdAndMembro_Id(grupoId, membroId)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado neste grupo."));

        inscricao.setPapelNoGrupo(novoPapel);
        inscricaoGrupoRepository.save(inscricao);
    }

    @Transactional
    public void removerMembro(String grupoId, String membroId, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        InscricaoGrupo inscricaoParaRemover = inscricaoGrupoRepository.findByGrupo_IdAndMembro_Id(grupoId, membroId)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado neste grupo."));

        boolean isCriador = grupo.getAssinanteCriadorGrupo().getId().equals(usuarioLogado.getId());
        boolean isProprioUsuario = usuarioLogado.getId().equals(membroId);

        if (!isCriador && !isProprioUsuario) {
            throw new AccessDeniedException("Você não tem permissão para remover este membro.");
        }

        if (grupo.getAssinanteCriadorGrupo().getId().equals(membroId)) {
            throw new BusinessException("O criador não pode sair ou ser removido do grupo.");
        }

        grupo.removeInscricao(inscricaoParaRemover);
        grupoRepository.save(grupo);
    }

    private void verificarLimiteDeMembros(Grupo grupo) {
        Assinante criador = grupo.getAssinanteCriadorGrupo();
        AssinaturaUsuario assinaturaVigente = assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(criador.getId(), StatusAssinaturaUsuarioEnum.getStatusVigentes(), LocalDateTime.now())
                .orElseThrow(() -> new BusinessException("O criador do grupo não possui uma assinatura ativa."));

        Integer limite = assinaturaVigente.getAssinaturaModelo().getLimiteMembrosPorGrupo();
        int contagemAtual = grupo.getInscricoes().size();

        if (contagemAtual >= limite) {
            throw new BusinessException("O grupo atingiu o limite de %d membros.".formatted(limite));
        }
    }

    @Transactional
    public GrupoResponseDTO editarGrupo(String grupoId, GrupoUpdateRequestDTO dto, Usuario usuarioLogado) {
        Grupo grupo = verificarCriador(grupoId, usuarioLogado);

        if (dto.titulo() != null && !dto.titulo().isBlank()) {
            grupo.setTitulo(dto.titulo());
        }

        if (dto.descricao() != null && !dto.descricao().isBlank()) {
            grupo.setDescricao(dto.descricao());
        }

        Grupo grupoSalvo = grupoRepository.save(grupo);
        return GrupoResponseDTO.fromEntity(grupoSalvo);
    }

    @Transactional
    public void excluirGrupo(String grupoId, Usuario usuarioLogado) {
        Grupo grupo = verificarCriador(grupoId, usuarioLogado);
        grupoRepository.delete(grupo);
    }

    private Grupo verificarCriador(String grupoId, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        if (!grupo.getAssinanteCriadorGrupo().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Apenas o criador pode modificar este grupo.");
        }

        return grupo;
    }

    @Transactional
    public GrupoResponseDTO criarSubGrupo(String grupoPaiId, SubGrupoRequestDTO dto, Usuario usuarioLogado) {
        Grupo grupoPai = grupoRepository.findById(grupoPaiId)
                .orElseThrow(() -> new NotFoundException("Grupo principal (TURMA) não foi encontrado."));

        if (!grupoPai.getAssinanteCriadorGrupo().getId().equals(usuarioLogado.getId())) {
            throw new AccessDeniedException("Apenas o professor criador da turma pode criar subgrupos.");
        }

        if (grupoPai.getTipoGrupo() != TipoGrupoEnum.TURMA) {
            throw new BusinessException("Subgrupos só podem ser criados a partir de uma TURMA.");
        }

        AssinaturaUsuario assinaturaVigente = getAssinaturaVigente(usuarioLogado.getId());
        if (!assinaturaVigente.getAssinaturaModelo().getPermiteCriarSubgrupos()) {
            throw new BusinessException("Seu plano de assinatura não permite a criação de subgrupos.");
        }

        validarMembrosDoSubgrupo(grupoPai, dto.idsDosMembros());

        Grupo subGrupo = new Grupo();
        subGrupo.setTitulo(dto.titulo());
        subGrupo.setDescricao(dto.descricao());
        subGrupo.setAssinanteCriadorGrupo((Assinante) usuarioLogado);
        subGrupo.setGrupoPai(grupoPai);
        subGrupo.setDisciplina(grupoPai.getDisciplina());
        subGrupo.setStatusGrupo(StatusGrupoEnum.ATIVO);

        subGrupo.setTipoGrupo(TipoGrupoEnum.GRUPO_ESTUDO);

        List<Assinante> membros = usuarioRepository.findAllById(dto.idsDosMembros()).stream()
                .map(u -> (Assinante) u)
                .toList();

        for (Assinante membro : membros) {
            InscricaoGrupo inscricao = new InscricaoGrupo();
            inscricao.setMembro(membro);
            inscricao.setPapelNoGrupo(PapelGrupoEnum.MEMBRO);
            subGrupo.addInscricao(inscricao);
        }

        Grupo subGrupoSalvo = grupoRepository.save(subGrupo);
        return GrupoResponseDTO.fromEntity(subGrupoSalvo);
    }

    private void validarMembrosDoSubgrupo(Grupo grupoPai, List<String> idsMembrosRequisitados) {
        Set<String> idsMembrosDaTurma = grupoPai.getInscricoes().stream()
                .map(inscricao -> inscricao.getMembro().getId())
                .collect(Collectors.toSet());

        for (String idRequisitado : idsMembrosRequisitados) {
            if (!idsMembrosDaTurma.contains(idRequisitado)) {
                throw new BusinessException("O usuário com ID '" + idRequisitado + "' não é membro da turma principal e não pode ser adicionado ao subgrupo.");
            }
        }
    }

    private AssinaturaUsuario getAssinaturaVigente(String assinanteId) {
        return assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(assinanteId,
                        StatusAssinaturaUsuarioEnum.getStatusVigentes(),
                        LocalDateTime.now())
                .orElseThrow(() -> new BusinessException("É necessário um plano de assinatura ativo."));
    }

    @Transactional
    public void adicionarAtividade(String grupoId, AdicionarAtividadeRequestDTO dto, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        AtividadeStrategy strategy = atividadeStrategyProvider.getStrategy(grupo.getTipoGrupo());

        strategy.adicionarAtividade(grupo, dto, usuarioLogado);
    }

    public List<MembroGrupoResponseDTO> listarMembrosDoGrupo(String grupoId, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        boolean isMembro = grupo.getInscricoes().stream()
                .anyMatch(i -> i.getMembro().getId().equals(usuarioLogado.getId()));
        if (!isMembro) {
            throw new AccessDeniedException("Apenas membros podem ver a lista de participantes do grupo.");
        }

        return grupo.getInscricoes().stream()
                .map(MembroGrupoResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }


    public List<AtividadeDoGrupoResponseDTO> listarAtividadesDoGrupo(String grupoId, Usuario usuarioLogado) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        ListarAtividadesStrategy strategy = listarAtividadesStrategyProvider.getStrategy(grupo.getTipoGrupo());

        return strategy.listarAtividades(grupo, usuarioLogado);
    }
}
