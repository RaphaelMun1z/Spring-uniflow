package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.res.EstudanteResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.dto.req.EstudanteRequestDTO;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.plansFy.exceptions.BusinessException;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.*;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstudanteService extends GenericCrudServiceImpl<EstudanteRequestDTO, EstudanteResponseDTO, Estudante, String> {
    @Autowired
    EstudanteRepository repository;

    @Autowired
    AssinaturaModeloRepository assinaturaModeloRepository;

    @Autowired
    AtividadeModeloRepository atividadeModeloRepository;

    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    @Autowired
    GrupoRepository grupoRepository;

    protected EstudanteService(EstudanteRepository repository) {
        super(repository, EstudanteRequestDTO::toModel, EstudanteResponseDTO::new);
    }

    @Override
    public EstudanteResponseDTO create(EstudanteRequestDTO data) {
        if (repository.existsByEmail(data.getEmail())) {
            throw new BusinessException("Estudante já registrado.");
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

        Estudante estudante = new Estudante();
        estudante.setNome(data.getNome());
        estudante.setEmail(data.getEmail());
        estudante.setPeriodo(data.getPeriodo());

        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, assinaturaModelo, LocalDateTime.now(), LocalDateTime.now(), true, estudante);
        estudante.setAssinaturaUsuario(assinaturaUsuario);

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
                        estudante))
                .collect(Collectors.toList());
        estudante.setAtividades(atividadesCopia);

        Estudante savedEstudante = repository.save(estudante);

        List<InscricaoGrupo> inscricoes = grupos.stream()
                .map(grupo -> new InscricaoGrupo(null, grupo, savedEstudante, LocalDateTime.now(), "Padrão"))
                .collect(Collectors.toList());
        List<InscricaoGrupo> savedInscricoes = inscricaoGrupoRepository.saveAll(inscricoes);

        savedEstudante.setGrupos(new HashSet<>(savedInscricoes));

        return new EstudanteResponseDTO(savedEstudante);
    }
}
