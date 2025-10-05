package io.github.raphaelmuniz.uniflow.controllers.usuario.docs;

import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinantePublicProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Assinantes", description = "Endpoints para consultar dados públicos de assinantes (usuários)")
public interface AssinanteControllerDocs {

    @Operation(summary = "Busca o perfil público de um assinante por ID", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssinantePublicProfileDTO.class)))
    @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Assinante não encontrado", content = @Content)
    ResponseEntity<AssinantePublicProfileDTO> buscarPerfilPublico(@Parameter(description = "ID do assinante a ser buscado") @PathVariable String id);

    @Operation(summary = "Lista todos os assinantes do sistema (Admin)", description = "Recurso administrativo para listar todos os assinantes (professores e estudantes) com paginação e filtro por tipo.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado (permissão 'ASSINANTE_LER' necessária)", content = @Content)
    ResponseEntity<PaginatedResponse<AssinantePublicProfileDTO>> listarAssinantes(@Parameter(description = "Filtra usuários pelo tipo ('PROFESSOR' ou 'ESTUDANTE')") @RequestParam(required = false) String tipo, Pageable pageable);
}