package io.github.raphaelmuniz.uniflow.services.validation.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.SignUpRequestDTO;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PapelRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsuarioFactory {
    private final PasswordEncoder passwordEncoder;
    private final PapelRepository papelRepository;

    public UsuarioFactory(PasswordEncoder passwordEncoder, PapelRepository papelRepository) {
        this.passwordEncoder = passwordEncoder;
        this.papelRepository = papelRepository;
    }

    public Usuario criarUsuario(SignUpRequestDTO dto) {
        return switch (dto.tipo()) {
            case ESTUDANTE -> buildEstudante(dto);
            case PROFESSOR -> buildProfessor(dto);
        };
    }

    private Estudante buildEstudante(SignUpRequestDTO dto) {
        if (dto.periodo() == null) {
            throw new BusinessException("O campo 'periodo' é obrigatório para o cadastro de Estudante.");
        }

        Papel papelEstudante = papelRepository.findByNome("ROLE_ESTUDANTE")
            .orElseThrow(() -> new NotFoundException("Papel 'ROLE_ESTUDANTE' não encontrado. Verifique os dados iniciais do sistema."));

        Estudante estudante = new Estudante();
        estudante.setNome(dto.nome());
        estudante.setEmail(dto.email());
        estudante.setSenha(passwordEncoder.encode(dto.senha()));
        estudante.setPeriodoDeIngresso(dto.periodo());
        estudante.setPapel(papelEstudante);
        return estudante;
    }

    private Professor buildProfessor(SignUpRequestDTO dto) {
        if (dto.areaAtuacao() == null || dto.areaAtuacao().isBlank()) {
            throw new BusinessException("O campo 'areaAtuacao' é obrigatório para o cadastro de Professor.");
        }

        Papel papelProfessor = papelRepository.findByNome("ROLE_PROFESSOR")
            .orElseThrow(() -> new NotFoundException("Papel 'ROLE_PROFESSOR' não encontrado. Verifique os dados iniciais do sistema."));

        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setEmail(dto.email());
        professor.setSenha(passwordEncoder.encode(dto.senha()));
        professor.setAreaAtuacao(dto.areaAtuacao());
        professor.setPapel(papelProfessor);
        return professor;
    }
}