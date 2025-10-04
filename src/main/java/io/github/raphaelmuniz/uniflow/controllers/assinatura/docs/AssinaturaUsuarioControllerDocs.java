package io.github.raphaelmuniz.uniflow.controllers.assinatura.docs;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface AssinaturaUsuarioControllerDocs {
    @Operation(summary = "Cadastra uma assinatura de usuário",
        description = "Recurso administrativo que permite o cadastro de uma assinatura de usuário",
        tags = {"Assinatura Usuário"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = AssinaturaUsuarioResponseDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<AssinaturaUsuarioResponseDTO> criarAssinaturaParaUsuario(@RequestBody @Valid AssinaturaUsuarioRequestDTO dto);

    @Operation(summary = "Busca todas as assinaturas de usuários",
        description = "Recurso administrativo que permite a busca por todas as assinaturas de usuários",
        tags = {"Assinatura Usuário"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = {
                    @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        array = @ArraySchema(schema = @Schema(implementation = AssinaturaUsuarioResponseDTO.class))
                    )
                }),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<PaginatedResponse<AssinaturaUsuarioResponseDTO>> listarTodas(Pageable pageable);

    @Operation(summary = "Busca uma assinatura de usuário",
        description = "Recurso administrativo que permite a busca por uma assinatura de usuário específica via ID",
        tags = {"Assinatura Usuário"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = AssinaturaUsuarioResponseDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<AssinaturaUsuarioResponseDTO> buscarPorId(@PathVariable String id);

    @Operation(summary = "Cancela uma assinatura de usuário",
        description = "Recurso administrativo que permite o cancelamento de uma assinatura de usuário",
        tags = {"Assinatura Usuário"},
        responses = {
            @ApiResponse(
                description = "Success",
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = AssinaturaUsuarioResponseDTO.class))
            ),
            @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
            @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
        }
    )
    ResponseEntity<AssinaturaUsuarioResponseDTO> cancelarAssinatura(@PathVariable String id);
}
