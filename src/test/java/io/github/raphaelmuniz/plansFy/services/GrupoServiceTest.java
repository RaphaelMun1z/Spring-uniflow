package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.*;
import io.github.raphaelmuniz.plansFy.entities.enums.DificuldadeEnum;
import io.github.raphaelmuniz.plansFy.entities.enums.StatusEntregaEnum;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinanteRepository;
import io.github.raphaelmuniz.plansFy.repositories.GrupoRepository;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@DisplayName("Testa o serviço do Grupo")
@ExtendWith(MockitoExtension.class)
public class GrupoServiceTest {
    @Mock
    private GrupoRepository repository;

    @Mock
    AssinanteRepository assinanteRepository;

    @InjectMocks
    private GrupoService service;

    private Grupo g0;
    private List<Estudante> estudantes;

    @BeforeEach
    public void setup() {
        Estudante e0 = new Estudante("e0", "Fulano", "fulano@gmail.com", null, null, null, 1);
        Estudante e1 = new Estudante("e1", "Ciclano", "ciclano@gmail.com", null, null, null, 3);
        Estudante e2 = new Estudante("e2", "Beltrano", "beltrano@gmail.com", null, null, null, 5);

        estudantes = new ArrayList<>();
        estudantes.add(e0);
        estudantes.add(e1);
        estudantes.add(e2);

        //AtividadeModelo am0 = new AtividadeModelo(LocalDateTime.now(), LocalDateTime.now(), "titulo", "descricao", DificuldadeEnum.DIFICIL, null, true);
        AtividadeCopia ac0 = new AtividadeCopia(null, StatusEntregaEnum.ENTREGUE);
        g0.setAtividades(List.of(ac0));

        g0 = new Grupo("g1", "Teste de título", "Grupo de estudo para estudantes", null, null);
        InscricaoGrupo ig0 = new InscricaoGrupo("ig0", g0, e0, LocalDateTime.now(), "Admin");
        InscricaoGrupo ig1 = new InscricaoGrupo("ig1", g0, e1, LocalDateTime.now(), "Padrão");
        InscricaoGrupo ig2 = new InscricaoGrupo("ig2", g0, e2, LocalDateTime.now(), "Padrão");

        Set<InscricaoGrupo> inscricoes = new HashSet<>(List.of(ig0, ig1, ig2));

        g0.setInscritos(inscricoes);
    }

    @DisplayName("Envia o Grupo padrão - deve salvar com sucesso")
    @Test
    void testGivenGrupoObject_WhenSaveGrupo_thenReturnGrupoObject() {
        // Given/Arrange
        given(assinanteRepository.findById("e1")).willReturn(Optional.of(estudantes.get(0)));
        given(assinanteRepository.findById("e2")).willReturn(Optional.of(estudantes.get(1)));
        given(assinanteRepository.findById("e3")).willReturn(Optional.of(estudantes.get(2)));
        given(repository.save(Mockito.any(Grupo.class))).willReturn(g0);

        // When/Act
        GrupoRequestDTO grupoRequestDTO = new GrupoRequestDTO(null, g0.getDescricao(), List.of("ac0"), List.of("i0"));
        GrupoResponseDTO grupoSalvo = service.create(grupoRequestDTO);

        // Then/Assert
        assertNotNull(grupoSalvo);
        assertEquals("Teste de título", grupoSalvo.getDescricao());
        assertEquals(3, grupoSalvo.getInscritos().size());
    }

    @DisplayName("Envia o Grupo com Aluno inexistente - deve lançar exception")
    @Test
    void testGivenInvalidGrupoObject_WhenSaveGrupo_thenReturnException() {
        // Given/Arrange
        given(assinanteRepository.findById("e4")).willReturn(Optional.empty());

        List<String> alunosId = List.of("e1", "e2", "e3");

        // When/Act
        assertThrows(NotFoundException.class, () -> {
            service.create(new GrupoRequestDTO(g0.getTitulo(), g0.getDescricao(), List.of("a1"), alunosId));
        });

        // Then/Assert
        verify(repository, never()).save(Mockito.any(Grupo.class));
    }

    @DisplayName("Busca por todos os Grupos - deve retornar com sucesso")
    @Test
    void testGivenGrupoList_WhenFindAllGrupo_thenReturnGrupoList() {
        // Given/Arrange
        Grupo g1 = new Grupo();
        given(repository.findAll()).willReturn(List.of(g0, g1));

        // When/Act
        List<GrupoResponseDTO> grupoList = service.findAll();

        // Then/Assert
        assertNotNull(grupoList);
        assertEquals(2, grupoList.size());
    }

    @DisplayName("Busca por todos os Grupos (sem registros) - deve retornar lista vazia")
    @Test
    void testGivenEmptyGrupoList_WhenFindAllGrupo_thenReturnEmptyGrupoList() {
        // Given/Arrange
        given(repository.findAll()).willReturn(Collections.emptyList());

        // When/Act
        List<GrupoResponseDTO> grupoList = service.findAll();

        // Then/Assert
        assertTrue(grupoList.isEmpty());
        assertEquals(0, grupoList.size());
    }

    @DisplayName("Envia o ID do Grupo - deve retornar o Grupo com sucesso")
    @Test
    void testGivenGrupoId_WhenFindById_thenReturnGrupoObject() {
        // Given/Arrange
        given(repository.findById(anyString())).willReturn(Optional.of(g0));

        // When/Act
        GrupoResponseDTO grupoSalvo = service.findById("g0");

        // Then/Assert
        assertNotNull(grupoSalvo);
        assertEquals("Grupo de estudo", grupoSalvo.getDescricao());
        assertEquals(2, grupoSalvo.getInscritos().size());
    }

    @DisplayName("Envia o ID do Grupo - deve deletar o Grupo com sucesso")
    @Test
    void testGivenGrupoId_WhenDeleteGrupo_thenDoNothing() {
        // Given/Arrange
        g0.setId("g0");
        willDoNothing().given(repository).deleteById(g0.getId());

        // When/Act
        service.delete(g0.getId());

        // Then/Assert
        verify(repository, times(1)).deleteById(g0.getId());
    }
}