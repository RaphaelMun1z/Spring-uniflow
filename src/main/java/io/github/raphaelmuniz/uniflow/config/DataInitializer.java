package io.github.raphaelmuniz.uniflow.config;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import io.github.raphaelmuniz.uniflow.entities.autorizacao.Permissao;
import io.github.raphaelmuniz.uniflow.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.uniflow.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.PapelGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.enums.TipoGrupoEnum;
import io.github.raphaelmuniz.uniflow.entities.grupo.Disciplina;
import io.github.raphaelmuniz.uniflow.entities.grupo.Grupo;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Admin;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PapelRepository;
import io.github.raphaelmuniz.uniflow.repositories.autorizacao.PermissaoRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.DisciplinaRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.GrupoRepository;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    private final GrupoRepository grupoRepository;
    private final DisciplinaRepository disciplinaRepository;

    public DataInitializer(PermissaoRepository permissaoRepository,
                           PapelRepository papelRepository,
                           UsuarioRepository usuarioRepository,
                           PasswordEncoder passwordEncoder,
                           GrupoRepository grupoRepository,
                           DisciplinaRepository disciplinaRepository) {
        this.permissaoRepository = permissaoRepository;
        this.papelRepository = papelRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.grupoRepository = grupoRepository;
        this.disciplinaRepository = disciplinaRepository;
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
        permissaoRepository.saveAll(List.of(pAdminGerenciar, pUserLer, pUserEscrever));
        log.info("Permissões criadas com sucesso.");

        log.info("Criando papéis...");
        Papel papelAdmin = new Papel(null, "ADMIN", Set.of(pAdminGerenciar, pUserLer, pUserEscrever));
        Papel papelProfessor = new Papel(null, "PROFESSOR", Set.of(pUserLer));
        Papel papelEstudante = new Papel(null, "ESTUDANTE", Set.of(pUserLer));
        papelRepository.saveAll(List.of(papelAdmin, papelProfessor, papelEstudante));
        log.info("Papéis criados e associados às permissões.");

        log.info("Criando usuários de exemplo...");
        String defaultPassword = passwordEncoder.encode("password");

        Admin admin = new Admin("Administrador", "admin@email.com", defaultPassword, papelAdmin);

        Professor professor = new Professor();
        professor.setNome("Professor Teste");
        professor.setEmail("professor@email.com");
        professor.setSenha(defaultPassword);
        professor.setPapel(papelProfessor);
        professor.setAreaAtuacao("Ciência da Computação");

        Estudante estudante = new Estudante();
        estudante.setNome("Estudante Teste");
        estudante.setEmail("estudante@email.com");
        estudante.setSenha(defaultPassword);
        estudante.setPapel(papelEstudante);
        estudante.setPeriodo(5);

        usuarioRepository.saveAll(List.of(admin, professor, estudante));
        log.info("Usuários de exemplo criados com sucesso.");

        log.info("Criando disciplina de exemplo...");
        Disciplina disciplinaExemplo = new Disciplina();
        disciplinaExemplo.setNome("Algoritmos e Estruturas de Dados");
        disciplinaExemplo.setPeriodo(3);
        disciplinaExemplo.setDificuldade(DificuldadeEnum.MEDIO);
        disciplinaExemplo.setPeriodoLetivo(new PeriodoLetivo(2025, 2));

        Disciplina disciplinaSalva = disciplinaRepository.save(disciplinaExemplo);
        log.info("Disciplina de exemplo criada com sucesso.");

        log.info("Criando grupo de exemplo...");
        Grupo grupoExemplo = new Grupo();
        grupoExemplo.setTitulo("Turma de Algoritmos 2025");
        grupoExemplo.setDescricao("Turma para a disciplina de Algoritmos e Estruturas de Dados.");
        grupoExemplo.setTipoGrupo(TipoGrupoEnum.TURMA);
        grupoExemplo.setStatusGrupo(StatusGrupoEnum.ATIVO);
        grupoExemplo.setCodigoConvite(Grupo.gerarCodigoConvite());
        grupoExemplo.setAssinanteCriadorGrupo(professor);
        grupoExemplo.setDisciplina(disciplinaSalva);

        InscricaoGrupo inscricaoEstudante = new InscricaoGrupo();
        inscricaoEstudante.setEstudanteMembro(estudante);
        inscricaoEstudante.setPapelNoGrupo(PapelGrupoEnum.MEMBRO);

        grupoExemplo.addInscricao(inscricaoEstudante);

        grupoRepository.save(grupoExemplo);
        log.info("Grupo de exemplo criado com o Professor como dono e o Estudante como membro.");

        log.info("----------------------------------------------------");
        log.info("Login: admin@email.com | Senha: password");
        log.info("Login: professor@email.com | Senha: password");
        log.info("Login: estudante@email.com | Senha: password");
        log.info("----------------------------------------------------");
    }
}