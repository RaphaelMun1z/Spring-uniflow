package io.github.raphaelmuniz.uniflow.controllers.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.PagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.RegistrarPagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.services.assinatura.PagamentoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.net.URI;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {
    private final PagamentoService pagamentoService;

    protected PagamentoController(PagamentoService service) {
        this.pagamentoService = service;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PagamentoResponseDTO> processarNovoPagamento(@RequestBody @Valid PagamentoRequestDTO dto) {
        PagamentoResponseDTO pagamentoProcessado = pagamentoService.processarNovoPagamento(dto);
        URI location = URI.create("/api/pagamentos/" + pagamentoProcessado.getId());
        return ResponseEntity.created(location).body(pagamentoProcessado);
    }

    @PostMapping("/registrar-externo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PagamentoResponseDTO> registrarPagamentoExterno(@RequestBody @Valid RegistrarPagamentoRequestDTO dto) {
        PagamentoResponseDTO pagamentoRegistrado = pagamentoService.registrarPagamentoExterno(dto);
        URI location = URI.create("/api/pagamentos/" + pagamentoRegistrado.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(pagamentoRegistrado);
    }

    @GetMapping("/assinantes/{assinanteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PaginatedResponse<PagamentoResponseDTO>> getHistoricoPagamentos(
            @PathVariable String assinanteId,
            @PageableDefault(size = 15, sort = "dataPagamento", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PagamentoResponseDTO> page = pagamentoService.listarHistoricoDePagamentos(assinanteId, pageable);
        PaginatedResponse<PagamentoResponseDTO> response = new PaginatedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getTotalPages(),
                page.getTotalElements()
        );
        return ResponseEntity.ok(response);
    }


}
