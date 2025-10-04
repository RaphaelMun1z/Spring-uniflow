package io.github.raphaelmuniz.uniflow.services.validation.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.GrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class GrupoFactory {
    private final DisciplinaRepository disciplinaRepository;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;
    private final GrupoRepository grupoRepository;
    private final List<GrupoCreationRule> validationRules;

    public GrupoFactory(
            DisciplinaRepository disciplinaRepository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository,
            GrupoRepository grupoRepository,
            List<GrupoCreationRule> validationRules) {
        this.disciplinaRepository = disciplinaRepository;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
        this.grupoRepository = grupoRepository;
        this.validationRules = validationRules;
    }

    public Grupo criarGrupo(GrupoRequestDTO data, Usuario usuarioAutenticado) {
        AssinaturaUsuario assinaturaVigente = getAssinaturaVigente(usuarioAutenticado.getId());
        AssinaturaModelo plano = assinaturaVigente.getAssinaturaModelo();
        long contagemDeGrupos = grupoRepository.countByAssinanteCriadorGrupoId(usuarioAutenticado.getId());

        GrupoValidationContext context = new GrupoValidationContext(usuarioAutenticado, data, plano, contagemDeGrupos);
        validationRules.forEach(rule -> rule.validate(context));

        Disciplina disciplina = disciplinaRepository.findById(data.disciplinaId())
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));

        Grupo novoGrupo = buildGrupo(data, (Assinante) usuarioAutenticado, disciplina);
        adicionarInscricaoDoCriador(novoGrupo, (Assinante) usuarioAutenticado);

        return novoGrupo;
    }

    private AssinaturaUsuario getAssinaturaVigente(String assinanteId) {
        return assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(assinanteId,
                        StatusAssinaturaUsuarioEnum.getStatusVigentes(),
                        LocalDateTime.now())
                .orElseThrow(() -> new BusinessException("É necessário um plano de assinatura ativo para criar grupos."));
    }

    private Grupo buildGrupo(GrupoRequestDTO data, Assinante criador, Disciplina disciplina) {
        Grupo grupo = new Grupo();
        grupo.setTitulo(data.titulo());
        grupo.setDescricao(data.descricao());
        grupo.setTipoGrupo(data.tipoGrupo());
        grupo.setAssinanteCriadorGrupo(criador);
        grupo.setDisciplina(disciplina);
        grupo.setStatusGrupo(StatusGrupoEnum.ATIVO);

        if (data.tipoGrupo() == TipoGrupoEnum.TURMA) {
            grupo.setCodigoConvite(Grupo.gerarCodigoConvite());
        }

        return grupo;
    }

    private void adicionarInscricaoDoCriador(Grupo grupo, Assinante criador) {
        InscricaoGrupo inscricaoCriador = new InscricaoGrupo();
        inscricaoCriador.setMembro(criador);

        if (grupo.getTipoGrupo() == TipoGrupoEnum.TURMA) {
            inscricaoCriador.setPapelNoGrupo(PapelGrupoEnum.PROFESSOR);
        } else {
            inscricaoCriador.setPapelNoGrupo(PapelGrupoEnum.ADMINISTRADOR);
        }

        grupo.addInscricao(inscricaoCriador);
    }
}