package io.github.raphaelmuniz.uniflow.services.atividade;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaUpdateRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeAvaliativaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeEntregaRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import io.github.raphaelmuniz.uniflow.services.usuario.AssinanteService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AtividadeAvaliativaService {
    private final AtividadeAvaliativaRepository atividadeAvaliativaRepository;
    private final AtividadeEntregaRepository atividadeEntregaRepository;
    private final GrupoRepository grupoRepository;
    private final AssinanteService assinanteService;

    protected AtividadeAvaliativaService(
            AtividadeAvaliativaRepository atividadeAvaliativaRepository,
            AtividadeEntregaRepository atividadeEntregaRepository,
            GrupoRepository grupoRepository,
            AssinanteService assinanteService) {
        this.atividadeAvaliativaRepository = atividadeAvaliativaRepository;
        this.atividadeEntregaRepository = atividadeEntregaRepository;
        this.grupoRepository = grupoRepository;
        this.assinanteService = assinanteService;
    }

    @Transactional
    public AtividadeAvaliativaResponseDTO criarEAssociarAtividade(String turmaId, AtividadeAvaliativaRequestDTO dto, Usuario professorLogado) {
        Grupo turma = grupoRepository.findById(turmaId)
                .orElseThrow(() -> new NotFoundException("Turma não encontrada."));

        if (turma.getTipoGrupo() != TipoGrupoEnum.TURMA) {
            throw new BusinessException("Atividades avaliativas só podem ser criadas em grupos do tipo TURMA.");
        }

        if (!turma.getAssinanteCriadorGrupo().getId().equals(professorLogado.getId())) {
            throw new AccessDeniedException("Apenas o professor criador da turma pode adicionar atividades.");
        }

        AssinaturaUsuario assinatura = assinanteService.obterAssinaturaVigenteEntidade(professorLogado.getId());
        if (!assinatura.getAssinaturaModelo().getPermiteTemplatesDeAtividade()) {
            throw new BusinessException("Seu plano de assinatura não permite esta funcionalidade.");
        }

        AtividadeAvaliativa novaAtividade = new AtividadeAvaliativa();

        if (dto.idAtividadeParaReutilizar() != null && !dto.idAtividadeParaReutilizar().isBlank()) {
            AtividadeAvaliativa original = atividadeAvaliativaRepository.findById(dto.idAtividadeParaReutilizar())
                    .orElseThrow(() -> new NotFoundException("Atividade original para reutilização não encontrada."));
            novaAtividade.setTitulo(original.getTitulo());
            novaAtividade.setDescricao(original.getDescricao());
            novaAtividade.setDificuldade(original.getDificuldade());
        } else {
            novaAtividade.setTitulo(dto.titulo());
            novaAtividade.setDescricao(dto.descricao());
            novaAtividade.setDificuldade(dto.dificuldade());
        }

        novaAtividade.setPrazoEntrega(dto.prazoEntrega());
        novaAtividade.setNotaMaxima(dto.notaMaxima());
        novaAtividade.setPermiteEnvioAtrasado(dto.permiteEnvioAtrasado());
        novaAtividade.setVisivibilidadeAtividade(dto.visibilidadeAtividade());

        novaAtividade.setGrupoPublicado(turma);
        novaAtividade.setAssinanteCriadorAtividade((Assinante) professorLogado);

        AtividadeAvaliativa atividadeSalva = atividadeAvaliativaRepository.save(novaAtividade);

        List<Estudante> estudantesDaTurma = turma.getInscricoes().stream()
                .map(InscricaoGrupo::getMembro)
                .filter(m -> m instanceof Estudante)
                .map(m -> (Estudante) m)
                .toList();

        if (!estudantesDaTurma.isEmpty()) {
            List<AtividadeEntrega> entregas = estudantesDaTurma.stream().map(estudante -> {
                AtividadeEntrega entrega = new AtividadeEntrega();
                entrega.setEstudanteDono(estudante);
                entrega.setAtividadeAvaliativaOrigem(atividadeSalva);
                entrega.setStatusEntrega(StatusEntregaEnum.PENDENTE);
                entrega.setDataEnvio(atividadeSalva.getPrazoEntrega());
                return entrega;
            }).collect(Collectors.toList());

            atividadeEntregaRepository.saveAll(entregas);
        }

        return new AtividadeAvaliativaResponseDTO(atividadeSalva);
    }

    @Transactional(readOnly = true)
    public AtividadeAvaliativaDetalhadaResponseDTO findById(String id, Usuario usuarioLogado) {
        AtividadeAvaliativa atividade = atividadeAvaliativaRepository.findByIdWithEntregas(id)
                .orElseThrow(() -> new NotFoundException("Atividade Avaliativa não encontrada."));

        boolean isCriador = atividade.getAssinanteCriadorAtividade().getId().equals(usuarioLogado.getId());
        boolean isAlunoDaTurma = atividade.getGrupoPublicado().getInscricoes().stream()
                .anyMatch(i -> i.getMembro().getId().equals(usuarioLogado.getId()));

        if (!isCriador && !isAlunoDaTurma) {
            throw new AccessDeniedException("Você não tem permissão para visualizar esta atividade.");
        }

        return new AtividadeAvaliativaDetalhadaResponseDTO(atividade);
    }

    @Transactional
    public AtividadeAvaliativaResponseDTO update(String id, AtividadeAvaliativaUpdateRequestDTO dto, Usuario professorLogado) {
        AtividadeAvaliativa atividade = atividadeAvaliativaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atividade Avaliativa não encontrada."));

        if (!atividade.getAssinanteCriadorAtividade().getId().equals(professorLogado.getId())) {
            throw new AccessDeniedException("Apenas o criador pode editar esta atividade.");
        }

        if (dto.titulo() != null) atividade.setTitulo(dto.titulo());
        if (dto.descricao() != null) atividade.setDescricao(dto.descricao());
        if (dto.notaMaxima() != null) atividade.setNotaMaxima(dto.notaMaxima());
        if (dto.permiteEnvioAtrasado() != null) atividade.setPermiteEnvioAtrasado(dto.permiteEnvioAtrasado());
        if (dto.dificuldade() != null) atividade.setDificuldade(dto.dificuldade());
        if (dto.visibilidadeAtividade() != null) atividade.setVisivibilidadeAtividade(dto.visibilidadeAtividade());

        if (dto.prazoEntrega() != null) {
            atividade.setPrazoEntrega(dto.prazoEntrega());
            atividade.getCopiasDosAssinantes().forEach(entrega -> {
                if (entrega.getStatusEntrega() == StatusEntregaEnum.PENDENTE) {
                    entrega.setPrazoEntrega(dto.prazoEntrega());
                }
            });
        }

        AtividadeAvaliativa atividadeAtualizada = atividadeAvaliativaRepository.save(atividade);
        return new AtividadeAvaliativaResponseDTO(atividadeAtualizada);
    }

    @Transactional
    public void delete(String id, Usuario professorLogado) {
        AtividadeAvaliativa atividade = atividadeAvaliativaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Atividade Avaliativa não encontrada."));

        if (!atividade.getAssinanteCriadorAtividade().getId().equals(professorLogado.getId())) {
            throw new AccessDeniedException("Apenas o criador pode excluir esta atividade.");
        }

        atividadeAvaliativaRepository.delete(atividade);
    }
}
