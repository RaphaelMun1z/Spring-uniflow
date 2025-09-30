package io.github.raphaelmuniz.uniflow.config;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import io.github.raphaelmuniz.uniflow.entities.usuario.Admin;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PapelRepository;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PermissaoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@Profile("test")
public class DataInitializer implements CommandLineRunner {

    private final PermissaoRepository permissaoRepository;
    private final PapelRepository papelRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(PermissaoRepository permissaoRepository, PapelRepository papelRepository, UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.permissaoRepository = permissaoRepository;
        this.papelRepository = papelRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() > 0) {
            log.info("O banco de dados já contém dados. A inicialização não será executada.");
            return;
        }

        log.info("Iniciando a criação de dados de teste...");

        log.info("Criando permissões...");
        Permissao pAdminGerenciar = new Permissao(null, "ADMIN_GERENCIAR_PAPEIS");
        Permissao pUserLer = new Permissao(null, "USUARIO_LER");
        Permissao pUserEscrever = new Permissao(null, "USUARIO_ESCREVER");
        Permissao pCursoLer = new Permissao(null, "CURSO_LER");
        Permissao pCursoEscrever = new Permissao(null, "CURSO_ESCREVER");

        permissaoRepository.saveAll(List.of(pAdminGerenciar, pUserLer, pUserEscrever, pCursoLer, pCursoEscrever));
        log.info("Permissões criadas com sucesso.");

        log.info("Criando papéis...");
        Papel papelAdmin = new Papel(null, "ADMIN", Set.of(pAdminGerenciar, pUserLer, pUserEscrever, pCursoLer, pCursoEscrever));
        Papel papelProfessor = new Papel(null, "PROFESSOR", Set.of(pUserLer, pCursoLer, pCursoEscrever));
        Papel papelEstudante = new Papel(null, "ESTUDANTE", Set.of(pUserLer, pCursoLer));

        papelRepository.saveAll(List.of(papelAdmin, papelProfessor, papelEstudante));
        log.info("Papéis criados e associados às permissões.");

        log.info("Criando usuários de exemplo...");
        String defaultPassword = passwordEncoder.encode("password");

        Admin admin = new Admin(
                "Administrador",
                "admin@email.com",
                defaultPassword,
                papelAdmin
        );

        Professor professor = new Professor(
                "Professor Teste",
                "professor@email.com",
                defaultPassword,
                new HashSet<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                "Ciência da Computação",
                papelProfessor
        );

        Estudante estudante = new Estudante(
                "Estudante Teste",
                "estudante@email.com",
                defaultPassword,
                new HashSet<>(),
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                new HashSet<>(),
                new ArrayList<>(),
                papelEstudante,
                5,
                new HashSet<>()
        );

        usuarioRepository.saveAll(List.of(admin, professor, estudante));
        log.info("Usuários de exemplo criados com sucesso.");
        log.info("----------------------------------------------------");
        log.info("Login: admin@email.com | Senha: password");
        log.info("Login: professor@email.com | Senha: password");
        log.info("Login: estudante@email.com | Senha: password");
        log.info("----------------------------------------------------");
    }
}