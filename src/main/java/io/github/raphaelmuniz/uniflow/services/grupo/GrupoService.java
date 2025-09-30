package io.github.raphaelmuniz.uniflow.services.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.AdicionarMembroGrupoDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResumeResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
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
import io.github.raphaelmuniz.uniflow.services.regras.grupo.RegraCriacaoGrupo;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class GrupoService extends GenericCrudServiceImpl<GrupoRequestDTO, GrupoResponseDTO, Grupo, String> {
    private static final int LIMITE_MAXIMO_DE_GRUPOS = 20;
    private static final int LIMITE_MAXIMO_DE_SUBGRUPOS = 10;

    private final GrupoRepository repository;
    private final AssinanteRepository assinanteRepository;
    private final EstudanteRepository estudanteRepository;
    private final InscricaoGrupoRepository inscricaoGrupoRepository;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    private final List<RegraCriacaoGrupo> regrasParaCriarGrupoPadrao;
    private final List<RegraCriacaoGrupo> regrasParaCriarSubGrupo;

    public GrupoService(
            GrupoRepository repository,
            AssinanteRepository assinanteRepository,
            EstudanteRepository estudanteRepository,
            InscricaoGrupoRepository inscricaoGrupoRepository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            @Qualifier("regrasGrupoPadrao") List<RegraCriacaoGrupo> regrasParaCriarGrupoPadrao,
            @Qualifier("regrasSubGrupo") List<RegraCriacaoGrupo> regrasParaCriarSubGrupo
    ) {
        super(repository, GrupoRequestDTO::toModel, GrupoResponseDTO::new);
        this.repository = repository;
        this.assinanteRepository = assinanteRepository;
        this.estudanteRepository = estudanteRepository;
        this.inscricaoGrupoRepository = inscricaoGrupoRepository;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.regrasParaCriarGrupoPadrao = regrasParaCriarGrupoPadrao;
        this.regrasParaCriarSubGrupo = regrasParaCriarSubGrupo;
    }

    @Override
    public GrupoResponseDTO create(GrupoRequestDTO data) {
        Assinante criador = assinanteRepository.findById(data.getCriadorId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado."));
        AssinaturaModelo planoDoCriador = assinaturaUsuarioRepository.findAssinaturaModeloAtivaByAssinanteId(criador.getId())
                .orElseThrow(() -> new BusinessException("Plano de assinatura ativo não encontrado."));

        regrasParaCriarGrupoPadrao.forEach(regra -> regra.verificar(null, criador, planoDoCriador));

        if (data.getEstudantesInscritosId().isEmpty()) {
            throw new ConstraintViolationException("É necessário ao menos um estudante inscrito.", Set.of());
        }

        List<Estudante> estudantesInscritos = estudanteRepository.findAllById(data.getEstudantesInscritosId());
        if (estudantesInscritos.size() != data.getEstudantesInscritosId().size()) {
            throw new NotFoundException("Algum estudante inscrito não encontrado.");
        }

        Grupo novoGrupo = new Grupo();
        novoGrupo.setTitulo(data.getTitulo());
        novoGrupo.setDescricao(data.getDescricao());
        novoGrupo.setTipoGrupo(data.getTipoGrupo());
        novoGrupo.setAssinanteCriadorGrupo(criador);

        estudantesInscritos.forEach(estudante -> {
            InscricaoGrupo inscricao = new InscricaoGrupo(null, LocalDateTime.now(), PapelGrupoEnum.MEMBRO, null, estudante);
            novoGrupo.addInscricao(inscricao);
        });

        if (data.getAtividadesGrupo() != null && !data.getAtividadesGrupo().isEmpty()) {
            data.getAtividadesGrupo().forEach(atividade -> {
                atividade.setGrupoPublicado(novoGrupo);
                atividade.setAssinanteCriadorAtividade(criador);
                novoGrupo.getAtividadesPublicadas().add(atividade);
            });
        }

        Grupo grupoSalvo = repository.save(novoGrupo);

        return new GrupoResponseDTO(grupoSalvo);
    }

    @Transactional
    public GrupoResponseDTO criarSubGrupo(String grupoPaiId, GrupoRequestDTO data) {
        Grupo grupoPai = repository.findById(grupoPaiId)
                .orElseThrow(() -> new NotFoundException("Grupo pai não encontrado."));
        Assinante criador = assinanteRepository.findById(data.getCriadorId())
                .orElseThrow(() -> new NotFoundException("Criador não encontrado."));
        AssinaturaModelo planoDoCriador = assinaturaUsuarioRepository.findAssinaturaModeloAtivaByAssinanteId(criador.getId())
                .orElseThrow(() -> new BusinessException("Plano de assinatura ativo não encontrado."));

        regrasParaCriarSubGrupo.forEach(regra -> regra.verificar(grupoPai, criador, planoDoCriador));

        Grupo novoSubGrupo = new Grupo();
        novoSubGrupo.setTitulo(data.getTitulo());
        novoSubGrupo.setDescricao(data.getDescricao());
        novoSubGrupo.setTipoGrupo(data.getTipoGrupo());
        novoSubGrupo.setAssinanteCriadorGrupo(criador);
        novoSubGrupo.setGrupoPai(grupoPai);

        if (data.getEstudantesInscritosId() != null && !data.getEstudantesInscritosId().isEmpty()) {
            List<Estudante> estudantesInscritos = estudanteRepository.findAllById(data.getEstudantesInscritosId());
            estudantesInscritos.forEach(estudante -> {
                InscricaoGrupo inscricao = new InscricaoGrupo(null, LocalDateTime.now(), PapelGrupoEnum.MEMBRO, novoSubGrupo, estudante);
                novoSubGrupo.addInscricao(inscricao);
            });
        }

        Grupo grupoSalvo = repository.save(novoSubGrupo);
        return new GrupoResponseDTO(grupoSalvo);
    }

    @Transactional
    public void adicionarMembroNoGrupo(AdicionarMembroGrupoDTO data) {
        Grupo grupo = repository.findById(data.getGrupoId()).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        Estudante estudanteEncontrado = estudanteRepository.findById(data.getEstudanteId()).orElseThrow(() -> new NotFoundException("Estudante não encontrado."));
        InscricaoGrupo inscricaoGrupo = new InscricaoGrupo(null, LocalDateTime.now(), data.getPapel(), grupo, estudanteEncontrado);
        grupo.addInscricao(inscricaoGrupo);
    }

    @Transactional
    public void removerMembroDoGrupo(String grupoId, String membroId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        InscricaoGrupo inscricaoEncontrada = inscricaoGrupoRepository.findByGrupo_IdAndEstudanteMembro_Id(grupoId, membroId).orElseThrow(() -> new NotFoundException("Inscrição não encontrada."));
        grupo.removeInscricao(inscricaoEncontrada);
    }

    public List<AssinanteResumeResponseDTO> listarMembrosDoGrupo(String grupoId) {
        Grupo grupo = repository.findByIdWithMembros(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getInscricoes().stream()
                .map(InscricaoGrupo::getEstudanteMembro)
                .map(AssinanteResumeResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AtividadeAvaliativaResponseDTO> listarAtividadesDoGrupo(String grupoId) {
        Grupo grupo = repository.findById(grupoId).orElseThrow(() -> new NotFoundException("Grupo não encontrado."));
        return grupo.getAtividadesPublicadas().stream().map(AtividadeAvaliativaResponseDTO::new).toList();
    }
}
