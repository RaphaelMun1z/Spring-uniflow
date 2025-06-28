package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Estudante;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteRequestDTO;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudanteService {
    @Autowired
    EstudanteRepository repository;

    //@Autowired
    //GrupoService grupoService;

    @Transactional
    public EstudanteResponseDTO create(EstudanteRequestDTO data) {
        Estudante saved = repository.save(data.toModel());
        return new EstudanteResponseDTO(saved);
    }

    public List<EstudanteResponseDTO> findAll() {
        return repository.findAll().stream().map(EstudanteResponseDTO::new).collect(Collectors.toList());
    }

    public EstudanteResponseDTO findById(String id) {
        Estudante created = repository.findById(id).orElseThrow(() -> new NotFoundException("Aluno não encontrado."));
        return new EstudanteResponseDTO(created);
    }

    @Transactional
    public void delete(String id) {
        if(repository.findById(id).isEmpty()){
            throw new NotFoundException("Estudante não encontrado.");
        }
        repository.deleteById(id);
    }

//    public List<GrupoResponseDTO> listarGruposInscritosPeloAluno(String alunoId){
//        return grupoService.findGruposByAlunoId(alunoId);
//    }
}
