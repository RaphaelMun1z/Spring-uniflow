package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.Estudante;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteRequestDTO;
import io.github.raphaelmuniz.plansFy.exceptions.BusinessException;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinaturaModeloRepository;
import io.github.raphaelmuniz.plansFy.repositories.EstudanteRepository;
import io.github.raphaelmuniz.plansFy.services.interfaces.CrudService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EstudanteService extends GenericCrudServiceImpl<EstudanteRequestDTO, EstudanteResponseDTO, Estudante, String> {
    @Autowired
    EstudanteRepository repository;

    @Autowired
    GrupoService grupoService;

    //@Autowired
    //AssinaturaModeloRepository assinaturaModeloRepository;

    protected EstudanteService(EstudanteRepository repository) {
        super(repository, EstudanteRequestDTO::toModel, EstudanteResponseDTO::new);
    }

    @Override
    public EstudanteResponseDTO create(EstudanteRequestDTO data) {
        Optional<Estudante> estudanteEncontrado = repository.findByEmail(data.getEmail());
        if(estudanteEncontrado.isPresent()){
            throw new BusinessException("Estudante já registrado.");
        }

        // Assinatura provisória
        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, new AssinaturaModelo(), LocalDateTime.now(), LocalDateTime.now(), true);
        Estudante estudante = new Estudante(null, data.getNome(), data.getEmail(), assinaturaUsuario, List.of(), Set.of(), data.getPeriodo());

        Estudante saved = repository.save(estudante);

        return new EstudanteResponseDTO(saved);
    }

//    public List<GrupoResponseDTO> listarGruposInscritosPeloAluno(String alunoId) {
//        if (repository.findById(alunoId).isEmpty()) {
//            throw new NotFoundException("Estudante não encontrado.");
//        }
//        return grupoService.findGruposByEstudanteId(alunoId);
//    }
}
