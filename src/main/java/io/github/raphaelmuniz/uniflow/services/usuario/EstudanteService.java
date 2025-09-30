package io.github.raphaelmuniz.uniflow.services.usuario;

import io.github.raphaelmuniz.uniflow.dto.res.usuario.EstudanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.req.usuario.EstudanteRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.EstudanteRepository;
import io.github.raphaelmuniz.uniflow.services.autorizacao.PapelService;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EstudanteService extends GenericCrudServiceImpl<EstudanteRequestDTO, EstudanteResponseDTO, Estudante, String> {
    @Autowired
    EstudanteRepository repository;

    @Autowired
    AssinaturaModeloRepository assinaturaModeloRepository;

    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    @Autowired
    GrupoRepository grupoRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PapelService papelService;

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

        List<Grupo> grupos = grupoRepository.findAllById(data.getGruposId());
        if (grupos.size() != data.getGruposId().size()) {
            throw new NotFoundException("Um ou mais Grupos não foram encontrados.");
        }

        Estudante estudante = new Estudante();
        estudante.setNome(data.getNome());
        estudante.setEmail(data.getEmail());
        estudante.setSenha(passwordEncoder.encode(data.getSenha()));
        estudante.setPeriodo(data.getPeriodo());
        estudante.setIsEnabled(true);
        estudante.setIsCredentialsNonExpired(true);
        estudante.setIsAccountNonLocked(true);
        estudante.setIsAccountNonExpired(true);
        Papel papelEstudante = papelService.findByNome("ESTUDANTE");
        estudante.setPapel(papelEstudante);

        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, assinaturaModelo, LocalDateTime.now(), LocalDateTime.now().plusMonths(1), true, estudante);
        estudante.getAssinaturas().add(assinaturaUsuario);

        Estudante savedEstudante = repository.save(estudante);

        List<InscricaoGrupo> inscricoes = grupos.stream()
                .map(grupo -> new InscricaoGrupo(null, LocalDateTime.now(), PapelGrupoEnum.MEMBRO, grupo, savedEstudante))
                .collect(Collectors.toList());
        List<InscricaoGrupo> savedInscricoesList = inscricaoGrupoRepository.saveAll(inscricoes);
        Set<InscricaoGrupo> savedInscricoes = new HashSet<>(savedInscricoesList);
        savedEstudante.setInscricoesGrupos(savedInscricoes);

        return new EstudanteResponseDTO(savedEstudante);
    }
}
