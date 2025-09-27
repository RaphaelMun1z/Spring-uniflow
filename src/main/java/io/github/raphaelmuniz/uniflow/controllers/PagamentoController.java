package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.services.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController extends GenericCrudControllerImpl<PagamentoRequestDTO, PagamentoResponseDTO> {
    @Autowired
    PagamentoService pagamentoService;

    protected PagamentoController(PagamentoService service) {
        super(service);
        this.pagamentoService = service;
    }

    @GetMapping("/assinantes/{assinanteId}/pagamentos")
    public ResponseEntity<PaginatedResponse<PagamentoResponseDTO>> getHistoricoPagamentos(
            @PathVariable String assinanteId,
            @PageableDefault(size = 10, sort = "dataPagamento", direction = Sort.Direction.DESC) Pageable pageable
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
