package io.github.raphaelmuniz.plansFy.config;

import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.entities.embeddables.PeriodoLetivo;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.plansFy.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
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
    private final AssinaturaModeloRepository assinaturaModeloRepository;
    private final AtividadeModeloRepository atividadeModeloRepository;
    private final GrupoRepository grupoRepository;
    private final DisciplinaRepository disciplinaRepository;
    private final EstudanteRepository estudanteRepository;
    private final InscricaoGrupoRepository inscricaoGrupoRepository;

    public DataInitializer(
            AssinaturaModeloRepository assinaturaModeloRepository,
            AtividadeModeloRepository atividadeModeloRepository,
            GrupoRepository grupoRepository,
            DisciplinaRepository disciplinaRepository,
            EstudanteRepository estudanteRepository,
            InscricaoGrupoRepository inscricaoGrupoRepository
    ) {
        this.assinaturaModeloRepository = assinaturaModeloRepository;
        this.atividadeModeloRepository = atividadeModeloRepository;
        this.grupoRepository = grupoRepository;
        this.disciplinaRepository = disciplinaRepository;
        this.estudanteRepository = estudanteRepository;
        this.inscricaoGrupoRepository = inscricaoGrupoRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (assinaturaModeloRepository.count() == 0) {
            AssinaturaModelo assinatura = new AssinaturaModelo(
                    null,
                    "Plano Premium",
                    "Acesso completo a todos os recursos",
                    new BigDecimal("99.90"),
                    12,
                    true,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            assinaturaModeloRepository.save(assinatura);
        }

        if (disciplinaRepository.count() == 0 && atividadeModeloRepository.count() == 0) {
            PeriodoLetivo periodo2025_1 = new PeriodoLetivo(2025, 1);
            PeriodoLetivo periodo2025_2 = new PeriodoLetivo(2025, 2);

            Disciplina matematica = new Disciplina(
                    null,
                    "Matemática Básica",
                    1,
                    DificuldadeEnum.MEDIO,
                    periodo2025_1
            );

            Disciplina portugues = new Disciplina(
                    null,
                    "Português Avançado",
                    2,
                    DificuldadeEnum.DIFICIL,
                    periodo2025_2
            );
            disciplinaRepository.saveAll(List.of(matematica, portugues));

            AtividadeModelo atividade1 = new AtividadeModelo(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(7),
                    "Lista de Exercícios 1",
                    "Resolver 10 questões de álgebra",
                    DificuldadeEnum.MEDIO,
                    matematica,
                    true
            );

            AtividadeModelo atividade2 = new AtividadeModelo(
                    LocalDateTime.now(),
                    LocalDateTime.now().plusDays(10),
                    "Redação Enem",
                    "Escrever uma redação sobre meio ambiente",
                    DificuldadeEnum.DIFICIL,
                    portugues,
                    true
            );
            atividadeModeloRepository.saveAll(List.of(atividade1, atividade2));
        }

        if (grupoRepository.count() == 0) {
            Grupo grupo1 = new Grupo(
                    null,
                    "Grupo de Estudos de Matemática",
                    "Focado em resolução de exercícios de cálculo",
                    new ArrayList<>(),
                    new HashSet<>()
            );

            Grupo grupo2 = new Grupo(
                    null,
                    "Grupo de Redação ENEM",
                    "Treinamento de redações semanais",
                    new ArrayList<>(),
                    new HashSet<>()
            );
            grupoRepository.saveAll(List.of(grupo1, grupo2));
        }

        AssinaturaModelo planoPremium = new AssinaturaModelo(
                null,
                "Plano Premium",
                "Acesso completo a todos os recursos",
                new BigDecimal("99.90"),
                12,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Estudante estudante1 = new Estudante(
                null,
                "João Silva",
                "joao.silva@ufu.br",
                null,
                new ArrayList<>(),
                new HashSet<>(),
                1
        );

        Estudante estudante2 = new Estudante(
                null,
                "Maria Oliveira",
                "maria.oliveira@ufu.br",
                null,
                new ArrayList<>(),
                new HashSet<>(),
                3
        );

        AssinaturaUsuario assinaturaUsuario1 = new AssinaturaUsuario(
                null,
                planoPremium,
                LocalDateTime.now(),
                LocalDateTime.now().plusMonths(12),
                true,
                estudante1
        );

        AssinaturaUsuario assinaturaUsuario2 = new AssinaturaUsuario(
                null,
                planoPremium,
                LocalDateTime.now(),
                LocalDateTime.now().plusMonths(6),
                true,
                estudante2
        );

        estudante1.setAssinaturaUsuario(assinaturaUsuario1);
        estudante2.setAssinaturaUsuario(assinaturaUsuario2);

        estudanteRepository.saveAll(List.of(estudante1, estudante2));
    }
}
