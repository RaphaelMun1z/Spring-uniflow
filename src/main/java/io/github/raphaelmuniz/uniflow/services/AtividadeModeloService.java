package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AtividadeModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.AtividadeModelo;
import io.github.raphaelmuniz.uniflow.entities.Disciplina;
import io.github.raphaelmuniz.uniflow.repositories.AtividadeModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtividadeModeloService extends GenericCrudServiceImpl<AtividadeModeloRequestDTO, AtividadeModeloResponseDTO, AtividadeModelo, String> {
    @Autowired
    AtividadeModeloRepository repository;

    @Autowired
    DisciplinaRepository disciplinaRepository;

    protected AtividadeModeloService(AtividadeModeloRepository repository) {
        super(repository, AtividadeModeloRequestDTO::toModel, AtividadeModeloResponseDTO::new);
    }

    @Override
    public AtividadeModeloResponseDTO create(AtividadeModeloRequestDTO data) {
        Disciplina disciplina = disciplinaRepository.findById(data.getDisciplinaId()).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
        AtividadeModelo atividadeModelo = new AtividadeModelo(data.getDataLancamento(), data.getPrazoEntrega(), data.getTitulo(), data.getDescricao(), data.getDificuldade(), disciplina, true);

        AtividadeModelo saved = repository.save(atividadeModelo);
        return new AtividadeModeloResponseDTO(saved);
    }

//    public List<AtividadeModeloResponseDTO> findByDisciplina(String disciplinaId) {
//        Disciplina disciplina = disciplinaRepository.findById(disciplinaId).orElseThrow(() -> new NotFoundException("Disciplina não encontrada."));
//
//        return repository.findByDisciplinaId(disciplina.getId()).stream().map(AtividadeModeloResponseDTO::new).collect(Collectors.toList());
//    }
}
