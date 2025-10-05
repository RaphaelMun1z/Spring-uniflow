package io.github.raphaelmuniz.uniflow.controllers.atividade;

import io.github.raphaelmuniz.uniflow.controllers.atividade.docs.AtividadeEntregaControllerDocs;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AtividadeEntregaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.atividade.AvaliacaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AtividadeEntregaDetalhadaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.atividade.AvaliacaoAtividadeResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Professor;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.services.atividade.AtividadeEntregaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/atividades-entrega")
public class AtividadeEntregaController implements AtividadeEntregaControllerDocs {
    private final AtividadeEntregaService atividadeEntregaService;

    protected AtividadeEntregaController(AtividadeEntregaService service) {
        this.atividadeEntregaService = service;
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Override
    public ResponseEntity<AtividadeEntregaDetalhadaResponseDTO> buscarPorId(
        @PathVariable String id,
        @AuthenticationPrincipal Usuario usuarioLogado) {
        AtividadeEntregaDetalhadaResponseDTO entrega = atividadeEntregaService.buscarPorId(id, usuarioLogado);
        return ResponseEntity.ok(entrega);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ATIVIDADE_ENTREGA_EDITAR')")
    @Override
    public ResponseEntity<Void> entregarAtividade(
        @PathVariable String id,
        @RequestBody @Valid AtividadeEntregaRequestDTO dto,
        @AuthenticationPrincipal Estudante estudanteLogado) {
        atividadeEntregaService.entregarAtividade(id, dto, estudanteLogado);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/avaliar")
    @PreAuthorize("hasAuthority('ATIVIDADE_ENTREGA_AVALIAR')")
    @Override
    public ResponseEntity<AvaliacaoAtividadeResponseDTO> avaliarEntrega(
        @PathVariable String id,
        @RequestBody @Valid AvaliacaoRequestDTO dto,
        @AuthenticationPrincipal Professor professorLogado) {
        AvaliacaoAtividadeResponseDTO avaliacao = atividadeEntregaService.avaliarEntrega(id, dto, professorLogado);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
    }
}
