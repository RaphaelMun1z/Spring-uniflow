package io.github.raphaelmuniz.uniflow.controllers.grupo;

import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeAvaliativaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.AlterarPapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.grupo.SubGrupoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeAvaliativaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.grupo.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeAvaliativaService;
import io.github.raphaelmuniz.uniflow.services.grupo.GrupoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/turmas")
@PreAuthorize("hasRole('PROFESSOR')")
public class TurmaController {
    private final AtividadeAvaliativaService atividadeAvaliativaService;
    private final GrupoService grupoService;

    public TurmaController(
            AtividadeAvaliativaService atividadeAvaliativaService,
            GrupoService grupoService) {
        this.atividadeAvaliativaService = atividadeAvaliativaService;
        this.grupoService = grupoService;
    }

    @PostMapping("/{id}/atividades-avaliativas")
    public ResponseEntity<AtividadeAvaliativaResponseDTO> criarAtividadeAvaliativa(
            @PathVariable("id") String turmaId,
            @RequestBody @Valid AtividadeAvaliativaRequestDTO dto,
            @AuthenticationPrincipal Usuario professorLogado) {
        AtividadeAvaliativaResponseDTO novaAtividade = atividadeAvaliativaService.criarEAssociarAtividade(turmaId, dto, professorLogado);
        URI location = URI.create(String.format("/api/atividades-avaliativas/%s", novaAtividade.getId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAtividade);
    }

    @PostMapping("/{id}/subgrupos")
    public ResponseEntity<GrupoResponseDTO> criarSubGrupo(
            @PathVariable("id") String turmaId,
            @RequestBody @Valid SubGrupoRequestDTO dto,
            @AuthenticationPrincipal Usuario professorLogado) {
        GrupoResponseDTO novoSubGrupo = grupoService.criarSubGrupo(turmaId, dto, professorLogado);
        URI location = URI.create("/api/grupos/" + novoSubGrupo.getId());
        return ResponseEntity.created(location).body(novoSubGrupo);
    }

    @PatchMapping("/{id}/membros/{membroId}")
    public ResponseEntity<Void> alterarPapelMembro(
            @PathVariable("id") String turmaId,
            @PathVariable String membroId,
            @RequestBody @Valid AlterarPapelRequestDTO dto,
            @AuthenticationPrincipal Usuario professorLogado) {
        grupoService.alterarPapelMembro(turmaId, membroId, dto.novoPapel(), professorLogado);
        return ResponseEntity.noContent().build();
    }
}