package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.Estudante;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteRequestDTO;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinaturaModeloRepository;
import io.github.raphaelmuniz.plansFy.repositories.EstudanteRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstudanteService {
    @Autowired
    EstudanteRepository repository;

    @Autowired
    GrupoService grupoService;

    @Autowired
    AssinaturaModeloRepository assinaturaModeloRepository;

    @Transactional
    public EstudanteResponseDTO create(EstudanteRequestDTO data) {
        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario();
        Estudante estudante = new Estudante(null, data.getNome(), data.getEmail(), assinaturaUsuario, List.of(), Set.of(), data.getPeriodo());
        Estudante saved = repository.save(estudante);
        return new EstudanteResponseDTO(saved);
    }

    public List<EstudanteResponseDTO> findAll() {
        return repository.findAll().stream().map(EstudanteResponseDTO::new).collect(Collectors.toList());
    }

    public EstudanteResponseDTO findById(String id) {
        Estudante created = repository.findById(id).orElseThrow(() -> new NotFoundException("Estudante não encontrado."));
        return new EstudanteResponseDTO(created);
    }

    @Transactional
    public void delete(String id) {
        if (repository.findById(id).isEmpty()) {
            throw new NotFoundException("Estudante não encontrado.");
        }
        repository.deleteById(id);
    }

    public List<GrupoResponseDTO> listarGruposInscritosPeloAluno(String alunoId) {
        if (repository.findById(alunoId).isEmpty()) {
            throw new NotFoundException("Estudante não encontrado.");
        }
        return grupoService.findGruposByAlunoId(alunoId);
    }
}
