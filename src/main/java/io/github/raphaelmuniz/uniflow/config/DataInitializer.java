package io.github.raphaelmuniz.uniflow.config;

import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@Slf4j
@Profile("test")
public class DataInitializer implements CommandLineRunner {
    private final UsuarioRepository usuarioRepository;
    private final PermissaoRepository permissaoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PermissaoRepository permissaoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.permissaoRepository = permissaoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (false) {
            log.info("Iniciando a criação de dados para o perfil de teste...");

            Permissao permissaoEstudante = new Permissao();
            permissaoEstudante.setDescricao("ROLE_ESTUDANTE");
            permissaoRepository.save(permissaoEstudante);

            Permissao permissaoProfessor = new Permissao();
            permissaoProfessor.setDescricao("ROLE_PROFESSOR");
            permissaoRepository.save(permissaoProfessor);

            Estudante estudante = new Estudante();
            estudante.setNome("Ana Silva");
            estudante.setEmail("ana.silva@email.com");
            estudante.setSenha(passwordEncoder.encode("senha123"));
            estudante.setPermissoes(List.of(permissaoEstudante));
            estudante.setIsAccountNonExpired(true);
            estudante.setIsAccountNonLocked(true);
            estudante.setIsCredentialsNonExpired(true);
            estudante.setIsEnabled(true);
            estudante.setPeriodo(5);
            usuarioRepository.save(estudante);
            log.info("Estudante 'Ana Silva' criado com sucesso.");

            Professor professor = new Professor();
            professor.setNome("Carlos Souza");
            professor.setEmail("carlos.souza@email.com");
            professor.setSenha(passwordEncoder.encode("senha456"));
            professor.setPermissoes(List.of(permissaoProfessor));
            professor.setIsAccountNonExpired(true);
            professor.setIsAccountNonLocked(true);
            professor.setIsCredentialsNonExpired(true);
            professor.setIsEnabled(true);
            professor.setAreaAtuacao("Engenharia de Software");
            usuarioRepository.save(professor);
            log.info("Professor 'Carlos Souza' criado com sucesso.");
        }
    }
}
