package io.github.raphaelmuniz.uniflow.services.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.AdicionarMembroGrupoDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.MembroGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResumeResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.EstudanteRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.config.ContextoCriacaoGrupo;
import io.github.raphaelmuniz.uniflow.services.regras.grupo.config.RegraCriacaoGrupo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GrupoService extends GenericCrudServiceImpl<GrupoRequestDTO, GrupoResponseDTO, Grupo, String> {
    private final GrupoRepository repository;
    private final AssinanteRepository assinanteRepository;
    private final EstudanteRepository estudanteRepository;
    private final InscricaoGrupoRepository inscricaoGrupoRepository;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    private final List<RegraCriacaoGrupo> regrasCriacaoGrupoPadrao;
    private final List<RegraCriacaoGrupo> regrasCriacaoSubGrupo;

    public GrupoService(
            GrupoRepository repository,
            AssinanteRepository assinanteRepository,
            EstudanteRepository estudanteRepository,
            InscricaoGrupoRepository inscricaoGrupoRepository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            @Qualifier("regrasCriacaoGrupoPadrao") List<RegraCriacaoGrupo> regrasCriacaoGrupoPadrao,
            @Qualifier("regrasCriacaoSubGrupo") List<RegraCriacaoGrupo> regrasCriacaoSubGrupo
    ) {
        super(repository, GrupoRequestDTO::toModel, GrupoResponseDTO::new);
        this.repository = repository;
        this.assinanteRepository = assinanteRepository;
        this.estudanteRepository = estudanteRepository;
        this.inscricaoGrupoRepository = inscricaoGrupoRepository;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.regrasCriacaoGrupoPadrao = regrasCriacaoGrupoPadrao;
        this.regrasCriacaoSubGrupo = regrasCriacaoSubGrupo;
    }

    @Override
    public GrupoResponseDTO create(GrupoRequestDTO data) {
        Assinante criador = assinanteRepository.findById(data.getCriadorId())
                .orElseThrow(() -> new NotFoundException("Assinante não encontrado."));

        ContextoCriacaoGrupo contexto = prepararContexto(null, criador);
        regrasCriacaoGrupoPadrao.forEach(regra -> regra.verificar(contexto));

        Grupo novoGrupo = montarNovoGrupo(data, criador);
        Grupo grupoSalvo = repository.save(novoGrupo);
        return new GrupoResponseDTO(grupoSalvo);
    }

    @Transactional
    public GrupoResponseDTO criarSubGrupo(String grupoPaiId, GrupoRequestDTO data) {
        Assinante criador = assinanteRepository.findById(data.getCriadorId())
                .orElseThrow(() -> new NotFoundException("Assinante não encontrado."));

        Grupo grupoPai = repository.findById(grupoPaiId)
                .orElseThrow(() -> new NotFoundException("Grupo pai não encontrado."));

        ContextoCriacaoGrupo contexto = prepararContexto(grupoPai, criador);
        regrasCriacaoSubGrupo.forEach(regra -> regra.verificar(contexto));

        Grupo novoSubGrupo = montarNovoGrupo(data, criador);
        Grupo grupoSalvo = repository.save(novoSubGrupo);
        return new GrupoResponseDTO(grupoSalvo);
    }

    private ContextoCriacaoGrupo prepararContexto(Grupo grupoPai, Assinante criador) {
        AssinaturaUsuario assinaturaUsuario = assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(criador.getId(),
                        StatusAssinaturaUsuarioEnum.getStatusVigentes(),
                        LocalDateTime.now())
                .orElseThrow(() -> new BusinessException("É necessário um plano de assinatura ativo."));

        AssinaturaModelo planoDoCriador = assinaturaUsuario.getAssinaturaModelo();
        return new ContextoCriacaoGrupo(grupoPai, criador, planoDoCriador);
    }

    private Grupo montarNovoGrupo(GrupoRequestDTO data, Assinante criador) {
        List<Estudante> estudantesInscritos = validarEstudantes(data.getEstudantesInscritosId());

        Grupo novoGrupo = inicializarGrupoBase(data, criador);

        adicionarInscricoes(novoGrupo, estudantesInscritos);
        adicionarAtividades(novoGrupo, data, criador);

        return novoGrupo;
    }

    private List<Estudante> validarEstudantes(List<String> estudantesIds) {
        if (estudantesIds == null || estudantesIds.isEmpty()) {
            throw new BusinessException("É necessário ao menos um estudante inscrito para criar um grupo.");
        }

        List<Estudante> estudantes = estudanteRepository.findAllById(estudantesIds);
        if (estudantes.size() != estudantesIds.size()) {
            throw new NotFoundException("Um ou mais estudantes informados não foram encontrados.");
        }
        return estudantes;
    }

    private Grupo inicializarGrupoBase(GrupoRequestDTO data, Assinante criador) {
        Grupo grupo = new Grupo();
        grupo.setTitulo(data.getTitulo());
        grupo.setDescricao(data.getDescricao());
        grupo.setTipoGrupo(data.getTipoGrupo());
        grupo.setAssinanteCriadorGrupo(criador);
        grupo.setStatusGrupo(StatusGrupoEnum.ATIVO);
        grupo.setCodigoConvite(Grupo.gerarCodigoConvite());
        return grupo;
    }

    private void adicionarInscricoes(Grupo grupo, List<Estudante> estudantes) {
        estudantes.forEach(estudante -> {
            InscricaoGrupo inscricao = new InscricaoGrupo();
            inscricao.setEstudanteMembro(estudante);
            inscricao.setPapelNoGrupo(PapelGrupoEnum.MEMBRO);
            grupo.addInscricao(inscricao);
        });
    }

    private void adicionarAtividades(Grupo grupo, GrupoRequestDTO data, Assinante criador) {
        if (data.getAtividadesGrupo() != null && !data.getAtividadesGrupo().isEmpty()) {
            data.getAtividadesGrupo().forEach(atividade -> {
                atividade.setAssinanteCriadorAtividade(criador);
                grupo.addAtividadePublicada(atividade);
            });
        }
    }

    @Transactional
    public void adicionarMembroNoGrupo(AdicionarMembroGrupoDTO data) {
        Grupo grupo = repository.findById(data.getGrupoId())
                .orElseThrow(() -> new NotFoundException("Grupo não encontrado."));

        Estudante estudante = estudanteRepository.findById(data.getEstudanteId())
                .orElseThrow(() -> new NotFoundException("Estudante não encontrado."));

        boolean jaExiste = inscricaoGrupoRepository
                .findByGrupo_IdAndEstudanteMembro_Id(grupo.getId(), estudante.getId())
                .isPresent();

        if (jaExiste) {
            throw new BusinessException("Estudante já está inscrito neste grupo.");
        }

        InscricaoGrupo inscricaoGrupo = new InscricaoGrupo(null, LocalDateTime.now(),
                data.getPapel(), grupo, estudante);

        grupo.addInscricao(inscricaoGrupo);
    }

    @Transactional
    public void removerMembroDoGrupo(String grupoId, String membroId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        InscricaoGrupo inscricaoEncontrada = inscricaoGrupoRepository.findByGrupo_IdAndEstudanteMembro_Id(grupoId, membroId).orElseThrow(() -> new NotFoundException("Inscrição não encontrada."));
        grupo.removeInscricao(inscricaoEncontrada);
    }

    @Transactional
    public void atualizarMembroDoGrupo(String grupoId, String membroId, PapelGrupoEnum novoPapelNoGrupo) {
        InscricaoGrupo inscricaoEncontrada = inscricaoGrupoRepository.findByGrupo_IdAndEstudanteMembro_Id(grupoId, membroId).orElseThrow(() -> new NotFoundException("Inscrição não encontrada."));
        inscricaoEncontrada.setPapelNoGrupo(novoPapelNoGrupo);
    }

    public List<MembroGrupoResponseDTO> listarMembrosDoGrupo(String grupoId) {
        Grupo grupo = repository.findByIdWithMembros(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getInscricoes().stream()
                .map(MembroGrupoResponseDTO::new)
                .toList();
    }

    public List<AtividadeAvaliativaResponseDTO> listarAtividadesDoGrupo(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getAtividadesPublicadas().stream().map(AtividadeAvaliativaResponseDTO::new).toList();
    }
}
