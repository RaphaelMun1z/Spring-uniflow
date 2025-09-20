package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.ProfessorRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.exceptions.BusinessException;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.*;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService extends GenericCrudServiceImpl<ProfessorRequestDTO, ProfessorResponseDTO, Professor, String> {
    @Autowired
    ProfessorRepository repository;

    @Autowired
    AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    @Autowired
    AtividadeModeloRepository atividadeModeloRepository;

    @Autowired
    GrupoRepository grupoRepository;

    protected ProfessorService(ProfessorRepository repository) {
        super(repository, ProfessorRequestDTO::toModel, ProfessorResponseDTO::new);
    }

    @Override
    public ProfessorResponseDTO create(ProfessorRequestDTO data) {
        Optional<Professor> professorEncontrado = repository.findByEmail(data.getEmail());
        if (professorEncontrado.isPresent()) {
            throw new BusinessException("Professor já registrado.");
        }

        Professor professor = new Professor(null, data.getNome(), data.getEmail(), null, null, null, data.getAreaAtuacao());

        // Assinatura
        //AssinaturaUsuario assinaturaUsuarioEncontrada = assinaturaUsuarioRepository.findById(data.getAssinaturaId()).orElseThrow(() -> new NotFoundException("Assinatura usuário não encontrada."));
        //professor.setAssinatura(assinaturaUsuarioEncontrada);
        professor.setAssinaturaUsuario(new AssinaturaUsuario());

        // Atividades
        List<AtividadeModelo> atividadesModeloEncontradas = atividadeModeloRepository.findAllById(data.getAtividadesId());
        if (atividadesModeloEncontradas.size() != data.getAtividadesId().size()) {
            throw new NotFoundException("Alguma atividade modelo não encontrada.");
        }

        // Criar as cópias de atividade

        // Grupos
        List<Grupo> gruposEncontrados = grupoRepository.findAllById(data.getGruposId());
        if (gruposEncontrados.size() != data.getGruposId().size()) {
            throw new NotFoundException("Algum grupo não encontrado.");
        }

        // Criar vinculo com grupos

        Professor saved = repository.save(data.toModel());
        return new ProfessorResponseDTO(saved);
    }
}
