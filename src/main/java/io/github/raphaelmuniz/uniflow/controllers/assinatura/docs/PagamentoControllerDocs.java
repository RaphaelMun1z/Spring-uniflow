package io.github.raphaelmuniz.uniflow.controllers.assinatura.docs;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.PagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.RegistrarPagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Pagamentos", description = "Endpoints para processar e consultar pagamentos")
public interface PagamentoControllerDocs {

    @Operation(summary = "Processa um novo pagamento para criar uma assinatura", description = "Endpoint para um usuário autenticado iniciar a compra de um novo plano de assinatura. Esta ação cria uma nova AssinaturaUsuario e o Pagamento correspondente.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Pagamento processado e assinatura criada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PagamentoResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos ou usuário já possui uma assinatura ativa", content = @Content)
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Assinante ou plano de assinatura não encontrado", content = @Content)
    ResponseEntity<PagamentoResponseDTO> processarNovoPagamento(@RequestBody @Valid PagamentoRequestDTO dto);

    @Operation(summary = "Registra um pagamento ocorrido externamente (Admin)", description = "Endpoint administrativo para registrar manualmente os detalhes de um pagamento, como os recebidos por um webhook.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Pagamento registrado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PagamentoResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados de requisição inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado (papel 'ADMIN' necessário)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Assinatura de usuário associada não encontrada", content = @Content)
    ResponseEntity<PagamentoResponseDTO> registrarPagamentoExterno(@RequestBody @Valid RegistrarPagamentoRequestDTO dto);

    @Operation(summary = "Lista o histórico de pagamentos de um usuário (Admin)", description = "Recurso administrativo para listar todos os pagamentos de um usuário específico, com paginação.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado (papel 'ADMIN' necessário)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Assinante não encontrado", content = @Content)
    ResponseEntity<PaginatedResponse<PagamentoResponseDTO>> getHistoricoPagamentos(@Parameter(description = "ID do assinante para buscar o histórico") @PathVariable String assinanteId, @PageableDefault(size = 15, sort = "dataPagamento", direction = Sort.Direction.DESC) Pageable pageable);
}