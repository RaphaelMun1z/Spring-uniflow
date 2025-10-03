package io.github.raphaelmuniz.uniflow.services.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.grupo.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.grupo.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisciplinaService {
    private final DisciplinaRepository disciplinaRepository;
    private final GrupoRepository grupoRepository;

    public DisciplinaService(DisciplinaRepository disciplinaRepository, GrupoRepository grupoRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.grupoRepository = grupoRepository;
    }

    @Transactional
    public DisciplinaResponseDTO criar(DisciplinaRequestDTO dto) {
        PeriodoLetivo periodoLetivo = new PeriodoLetivo(dto.anoLetivo(), dto.semestreLetivo());

        if (disciplinaRepository.existsByNomeAndPeriodoAndPeriodoLetivo(dto.nome(), dto.periodo(), periodoLetivo)) {
            throw new BusinessException("Já existe uma disciplina com o mesmo nome, período e período letivo.");
        }

        Disciplina novaDisciplina = new Disciplina();
        novaDisciplina.setNome(dto.nome());
        novaDisciplina.setPeriodo(dto.periodo());
        novaDisciplina.setDificuldade(dto.dificuldade());
        novaDisciplina.setPeriodoLetivo(periodoLetivo);

        Disciplina disciplinaSalva = disciplinaRepository.save(novaDisciplina);
        return DisciplinaResponseDTO.fromEntity(disciplinaSalva);
    }

    @Transactional(readOnly = true)
    public Page<DisciplinaResponseDTO> buscarTodas(Pageable pageable) {
        Page<Disciplina> disciplinas = disciplinaRepository.findAll(pageable);
        return disciplinas.map(DisciplinaResponseDTO::fromEntity);
    }

    @Transactional(readOnly = true)
    public DisciplinaResponseDTO buscarPorId(String id) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada com o ID: " + id));
        return DisciplinaResponseDTO.fromEntity(disciplina);
    }

    @Transactional
    public DisciplinaResponseDTO atualizar(String id, DisciplinaRequestDTO dto) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Disciplina não encontrada com o ID: " + id));

        disciplina.setNome(dto.nome());
        disciplina.setPeriodo(dto.periodo());
        disciplina.setDificuldade(dto.dificuldade());
        disciplina.setPeriodoLetivo(new PeriodoLetivo(dto.anoLetivo(), dto.semestreLetivo()));

        Disciplina disciplinaAtualizada = disciplinaRepository.save(disciplina);
        return DisciplinaResponseDTO.fromEntity(disciplinaAtualizada);
    }

    @Transactional
    public void deletar(String id) {
        if (!disciplinaRepository.existsById(id)) {
            throw new NotFoundException("Disciplina não encontrada com o ID: " + id);
        }

        if (grupoRepository.existsByDisciplina_Id(id)) {
            throw new BusinessException("Não é possível excluir uma disciplina que já está associada a um ou mais grupos.");
        }

        disciplinaRepository.deleteById(id);
    }
}
