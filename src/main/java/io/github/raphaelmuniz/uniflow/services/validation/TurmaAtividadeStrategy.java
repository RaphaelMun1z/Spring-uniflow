package io.github.raphaelmuniz.uniflow.services.validation;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.AdicionarAtividadeRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeAvaliativaRepository;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeEntregaRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TurmaAtividadeStrategy implements AtividadeStrategy {

    private final AtividadeAvaliativaRepository atividadeAvaliativaRepository;
    private final AtividadeEntregaRepository atividadeEntregaRepository;
    private final AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    public TurmaAtividadeStrategy(
            AtividadeAvaliativaRepository atividadeAvaliativaRepository,
            AtividadeEntregaRepository atividadeEntregaRepository,
            AssinaturaUsuarioRepository assinaturaUsuarioRepository) {
        this.atividadeAvaliativaRepository = atividadeAvaliativaRepository;
        this.atividadeEntregaRepository = atividadeEntregaRepository;
        this.assinaturaUsuarioRepository = assinaturaUsuarioRepository;
    }

    @Override
    public TipoGrupoEnum supports() {
        return TipoGrupoEnum.TURMA;
    }

    @Override
    @Transactional
    public void adicionarAtividade(Grupo grupo, AdicionarAtividadeRequestDTO dto, Usuario usuarioLogado) {
        if (dto.tipo() != AdicionarAtividadeRequestDTO.TipoAtividade.AVALIATIVA || dto.avaliativa() == null) {
            throw new BusinessException("Requisição inválida para adicionar atividade em uma TURMA.");
        }

        grupo.getInscricoes().stream()
                .filter(i -> i.getMembro().getId().equals(usuarioLogado.getId()) && i.getPapelNoGrupo() == PapelGrupoEnum.PROFESSOR)
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("Apenas o professor da turma pode adicionar atividades."));

        AssinaturaUsuario assinatura = getAssinaturaVigente(usuarioLogado.getId());
        if (!assinatura.getAssinaturaModelo().getPermiteTemplatesDeAtividade()) {
            throw new BusinessException("Seu plano não permite o uso de templates de atividade.");
        }

        AtividadeAvaliativa atividade = atividadeAvaliativaRepository.findById(dto.avaliativa().atividadeAvaliativaId())
                .orElseThrow(() -> new NotFoundException("Atividade Avaliativa não encontrada."));

        List<Estudante> estudantesDaTurma = grupo.getInscricoes().stream()
                .map(InscricaoGrupo::getMembro)
                .filter(m -> m instanceof Estudante)
                .map(m -> (Estudante) m)
                .toList();

        List<AtividadeEntrega> entregas = estudantesDaTurma.stream().map(estudante -> {
            AtividadeEntrega entrega = new AtividadeEntrega();
            entrega.setEstudanteDono(estudante);
            entrega.setAtividadeAvaliativaOrigem(atividade);
            entrega.setStatusEntrega(StatusEntregaEnum.PENDENTE);
            entrega.setPrazoEntrega(atividade.getPrazoEntrega());
            return entrega;
        }).collect(Collectors.toList());

        atividadeEntregaRepository.saveAll(entregas);
    }

    private AssinaturaUsuario getAssinaturaVigente(String assinanteId) {
        return assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(assinanteId,
                        StatusAssinaturaUsuarioEnum.getStatusVigentes(),
                        LocalDateTime.now())
                .orElseThrow(() -> new BusinessException("É necessário um plano de assinatura ativo."));
    }
}