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
        return switch (dto.getTipo()) {
            case ESTUDANTE -> buildEstudante(dto);
            case PROFESSOR -> buildProfessor(dto);
        };
    }

    private Estudante buildEstudante(SignUpRequestDTO dto) {
        if (dto.getPeriodo() == null) {
            throw new BusinessException("O campo 'periodo' é obrigatório para o cadastro de Estudante.");
        }

        Papel papelEstudante = papelRepository.findByNome("ESTUDANTE")
                .orElseThrow(() -> new NotFoundException("Papel 'ESTUDANTE' não encontrado. Verifique os dados iniciais do sistema."));

        Estudante estudante = new Estudante();
        estudante.setNome(dto.getNome());
        estudante.setEmail(dto.getEmail());
        estudante.setSenha(passwordEncoder.encode(dto.getSenha()));
        estudante.setPeriodo(dto.getPeriodo());
        estudante.setPapel(papelEstudante);
        return estudante;
    }

    private Professor buildProfessor(SignUpRequestDTO dto) {
        if (dto.getAreaAtuacao() == null || dto.getAreaAtuacao().isBlank()) {
            throw new BusinessException("O campo 'areaAtuacao' é obrigatório para o cadastro de Professor.");
        }

        Papel papelProfessor = papelRepository.findByNome("PROFESSOR")
                .orElseThrow(() -> new NotFoundException("Papel 'PROFESSOR' não encontrado. Verifique os dados iniciais do sistema."));

        Professor professor = new Professor();
        professor.setNome(dto.getNome());
        professor.setEmail(dto.getEmail());
        professor.setSenha(passwordEncoder.encode(dto.getSenha()));
        professor.setAreaAtuacao(dto.getAreaAtuacao());
        professor.setPapel(papelProfessor);
        return professor;
    }
}