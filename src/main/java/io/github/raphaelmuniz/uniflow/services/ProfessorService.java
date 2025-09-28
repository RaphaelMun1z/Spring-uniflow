package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.ProfessorRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.uniflow.exceptions.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.*;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessorService extends GenericCrudServiceImpl<ProfessorRequestDTO, ProfessorResponseDTO, Professor, String> {
    @Autowired
    ProfessorRepository repository;

    @Autowired
    AssinaturaModeloRepository assinaturaModeloRepository;

    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    @Autowired
    GrupoRepository grupoRepository;

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

        List<Grupo> grupos = grupoRepository.findAllById(data.getGruposId());
        if (grupos.size() != data.getGruposId().size()) {
            throw new NotFoundException("Um ou mais Grupos não foram encontrados.");
        }

        Professor professor = new Professor();
        professor.setNome(data.getNome());
        professor.setEmail(data.getEmail());
        professor.setSenha(passwordEncoder.encode(data.getSenha()));
        professor.setAreaAtuacao(data.getAreaAtuacao());

        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, assinaturaModelo, LocalDateTime.now(), LocalDateTime.now(), true, professor);
        professor.getAssinaturas().add(assinaturaUsuario);

        Professor savedProfessor = repository.save(professor);

        List<InscricaoGrupo> inscricoes = grupos.stream()
                .map(grupo -> new InscricaoGrupo(null, LocalDateTime.now(), PapelGrupoEnum.MEMBRO, grupo, savedProfessor))
                .collect(Collectors.toList());
        List<InscricaoGrupo> savedInscricoes = inscricaoGrupoRepository.saveAll(inscricoes);
        savedProfessor.setInscricoesGrupos(new HashSet<>(savedInscricoes));

        return new ProfessorResponseDTO(savedProfessor);
    }
}
