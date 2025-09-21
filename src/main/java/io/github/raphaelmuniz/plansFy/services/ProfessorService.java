package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.ProfessorRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.ProfessorResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.plansFy.exceptions.BusinessException;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.*;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService extends GenericCrudServiceImpl<ProfessorRequestDTO, ProfessorResponseDTO, Professor, String> {
    @Autowired
    ProfessorRepository repository;

    @Autowired
    AssinaturaModeloRepository assinaturaModeloRepository;

    @Autowired
    AtividadeModeloRepository atividadeModeloRepository;

    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    @Autowired
    GrupoRepository grupoRepository;

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

        List<AtividadeModelo> atividadesModelo = atividadeModeloRepository.findAllById(data.getAtividadesId());
        if (atividadesModelo.size() != data.getAtividadesId().size()) {
            throw new NotFoundException("Uma ou mais Atividades Modelo não foram encontradas.");
        }

        List<Grupo> grupos = grupoRepository.findAllById(data.getGruposId());
        if (grupos.size() != data.getGruposId().size()) {
            throw new NotFoundException("Um ou mais Grupos não foram encontrados.");
        }

        Professor professor = new Professor();
        professor.setNome(data.getNome());
        professor.setEmail(data.getEmail());
        professor.setAreaAtuacao(data.getAreaAtuacao());

        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, assinaturaModelo, LocalDateTime.now(), LocalDateTime.now(), true, professor);
        professor.setAssinaturaUsuario(assinaturaUsuario);

        List<AtividadeCopia> atividadesCopia = atividadesModelo.stream()
                .map(modelo -> new AtividadeCopia(
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        modelo.getTitulo(),
                        modelo.getDescricao(),
                        modelo.getDificuldade(),
                        modelo.getDisciplina(),
                        null,
                        StatusEntregaEnum.PENDENTE,
                        null,
                        professor))
                .collect(Collectors.toList());
        professor.setAtividades(atividadesCopia);

        Professor savedProfessor = repository.save(professor);

        List<InscricaoGrupo> inscricoes = grupos.stream()
                .map(grupo -> new InscricaoGrupo(null, grupo, savedProfessor, LocalDateTime.now(), "Padrão"))
                .collect(Collectors.toList());
        List<InscricaoGrupo> savedInscricoes = inscricaoGrupoRepository.saveAll(inscricoes);
        savedProfessor.setGrupos(new HashSet<>(savedInscricoes));

        return new ProfessorResponseDTO(savedProfessor);
    }
}
