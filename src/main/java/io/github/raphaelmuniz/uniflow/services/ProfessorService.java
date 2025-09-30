package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.ProfessorRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.exceptions.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfessorService extends GenericCrudServiceImpl<ProfessorRequestDTO, ProfessorResponseDTO, Professor, String> {
    @Autowired
    ProfessorRepository repository;

    @Autowired
    AssinaturaModeloRepository assinaturaModeloRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected ProfessorService(ProfessorRepository repository) {
        super(repository, ProfessorRequestDTO::toModel, ProfessorResponseDTO::new);
    }

    @Override
    public ProfessorResponseDTO create(ProfessorRequestDTO data) {
        if (repository.existsByEmail(data.getEmail())) {
            throw new BusinessException("Professor já registrado.");
        }

        AssinaturaModelo assinaturaModelo = assinaturaModeloRepository.findById(data.getAssinaturaId())
                .orElseThrow(() -> new NotFoundException("Assinatura Modelo não encontrada."));

        Professor professor = new Professor();
        professor.setNome(data.getNome());
        professor.setEmail(data.getEmail());
        professor.setSenha(passwordEncoder.encode(data.getSenha()));
        professor.setAreaAtuacao(data.getAreaAtuacao());

        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, assinaturaModelo, LocalDateTime.now(), LocalDateTime.now(), true, professor);
        professor.getAssinaturas().add(assinaturaUsuario);

        Professor savedProfessor = repository.save(professor);
        return new ProfessorResponseDTO(savedProfessor);
    }
}
