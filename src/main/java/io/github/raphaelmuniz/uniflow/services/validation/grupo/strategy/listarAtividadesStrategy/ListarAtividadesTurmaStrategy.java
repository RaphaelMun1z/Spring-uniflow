package io.github.raphaelmuniz.uniflow.services.validation.grupo.strategy.listarAtividadesStrategy;

import io.github.raphaelmuniz.uniflow.dto.res.grupo.AtividadeDoGrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeAvaliativa;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.repositories.atividade.AtividadeEntregaRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ListarAtividadesTurmaStrategy implements ListarAtividadesStrategy {

    private final AtividadeEntregaRepository atividadeEntregaRepository;

    public ListarAtividadesTurmaStrategy(AtividadeEntregaRepository atividadeEntregaRepository) {
        this.atividadeEntregaRepository = atividadeEntregaRepository;
    }

    @Override
    public TipoGrupoEnum supports() {
        return TipoGrupoEnum.TURMA;
    }

    @Override
    public List<AtividadeDoGrupoResponseDTO> listarAtividades(Grupo grupo, Usuario usuarioLogado) {
        boolean isMembro = grupo.getInscricoes().stream()
                .anyMatch(i -> i.getMembro().getId().equals(usuarioLogado.getId()));
        if (!isMembro) {
            throw new AccessDeniedException("Você não é membro deste grupo.");
        }

        Set<AtividadeAvaliativa> atividades = grupo.getAtividadesPublicadas();
        if (atividades == null || atividades.isEmpty()) {
            return Collections.emptyList();
        }

        if (usuarioLogado instanceof Professor) {
            return listarAtividadesParaProfessor(grupo, atividades);
        } else if (usuarioLogado instanceof Estudante) {
            return listarAtividadesParaEstudante(atividades, (Estudante) usuarioLogado);
        }

        return Collections.emptyList();
    }

    private List<AtividadeDoGrupoResponseDTO> listarAtividadesParaProfessor(Grupo grupo, Set<AtividadeAvaliativa> atividades) {
        long totalAlunos = grupo.getInscricoes().stream()
                .filter(i -> i.getMembro() instanceof Estudante)
                .count();

        return atividades.stream().map(atividade -> {
            Set<StatusEntregaEnum> statuses = Set.of(StatusEntregaEnum.ENTREGUE, StatusEntregaEnum.AVALIADO);

            int totalEntregas = atividadeEntregaRepository
                    .countByAtividadeAvaliativaOrigem_IdAndStatusEntregaIn(atividade.getId(), statuses);

            return AtividadeDoGrupoResponseDTO.builder()
                    .id(atividade.getId())
                    .titulo(atividade.getTitulo())
                    .tipo("AVALIATIVA")
                    .resumoProfessor(new AtividadeDoGrupoResponseDTO.ResumoProfessor(totalEntregas, (int) totalAlunos))
                    .build();
        }).collect(Collectors.toList());
    }

    private List<AtividadeDoGrupoResponseDTO> listarAtividadesParaEstudante(Set<AtividadeAvaliativa> atividades, Estudante estudante) {
        return atividades.stream().map(atividade -> {
            Optional<AtividadeEntrega> minhaEntregaOpt = atividadeEntregaRepository
                    .findByAtividadeAvaliativaOrigem_IdAndEstudanteDono_Id(atividade.getId(), estudante.getId());

            AtividadeDoGrupoResponseDTO.StatusAluno statusAluno;

            if (minhaEntregaOpt.isPresent()) {
                AtividadeEntrega minhaEntrega = minhaEntregaOpt.get();
                String notaFormatada = Optional.ofNullable(minhaEntrega.getAvaliacaoAtividade())
                        .map(avaliacao -> avaliacao.getNota() != null ? avaliacao.getNota().toString() : "-")
                        .orElse("-");
                statusAluno = new AtividadeDoGrupoResponseDTO.StatusAluno(minhaEntrega.getStatusEntrega().toString(), notaFormatada);
            } else {
                statusAluno = new AtividadeDoGrupoResponseDTO.StatusAluno(StatusEntregaEnum.PENDENTE.toString(), "-");
            }

            return AtividadeDoGrupoResponseDTO.builder()
                    .id(atividade.getId())
                    .titulo(atividade.getTitulo())
                    .tipo("AVALIATIVA")
                    .statusAluno(statusAluno)
                    .build();
        }).collect(Collectors.toList());
    }
}