package io.github.raphaelmuniz.uniflow.controllers.usuario.docs;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PaginatedResponse;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.autorizacao.PermissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinantePublicProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Tag(name = "Admin", description = "Endpoints para gerenciamento administrativo do sistema")
public interface AdminControllerDocs {

    @Operation(summary = "Lista todos os usuários do sistema", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<PaginatedResponse<AssinantePublicProfileDTO>> listarUsuarios(@Parameter(description = "Filtra usuários pelo tipo ('PROFESSOR' ou 'ESTUDANTE')") @RequestParam(required = false) String tipo, Pageable pageable);

    @Operation(summary = "Lista todos os planos de assinatura", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Planos"})
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = AssinaturaModeloResponseDTO.class))))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<List<AssinaturaModeloResponseDTO>> listarPlanos();

    @Operation(summary = "Cria um novo plano de assinatura", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Planos"})
    @ApiResponse(responseCode = "201", description = "Plano criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssinaturaModeloResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<AssinaturaModeloResponseDTO> criarPlano(@RequestBody @Valid AssinaturaModeloRequestDTO dto);

    @Operation(summary = "Atualiza um plano de assinatura existente", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Planos"})
    @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssinaturaModeloResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Plano não encontrado", content = @Content)
    ResponseEntity<AssinaturaModeloResponseDTO> atualizarPlano(@Parameter(description = "ID do plano a ser atualizado") @PathVariable String id, @RequestBody @Valid AssinaturaModeloRequestDTO dto);

    @Operation(summary = "Deleta um plano de assinatura", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Planos"})
    @ApiResponse(responseCode = "204", description = "Plano deletado com sucesso", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Plano não encontrado", content = @Content)
    ResponseEntity<Void> deletarPlano(@Parameter(description = "ID do plano a ser deletado") @PathVariable String id);

    @Operation(summary = "Lista todos os papéis do sistema", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Autorização"})
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PapelResponseDTO.class))))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<List<PapelResponseDTO>> listarPapeis();

    @Operation(summary = "Cria um novo papel no sistema", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Autorização"})
    @ApiResponse(responseCode = "201", description = "Papel criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PapelResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<PapelResponseDTO> criarPapel(@RequestBody @Valid PapelRequestDTO dto);

    @Operation(summary = "Lista todas as permissões do sistema", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Autorização"})
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = PermissaoResponseDTO.class))))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<List<PermissaoResponseDTO>> listarPermissoes();

    @Operation(summary = "Lista todas as assinaturas de usuários", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Assinaturas de Usuários"})
    @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = PaginatedResponse.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<PaginatedResponse<AssinaturaUsuarioResponseDTO>> listarAssinaturasDeUsuarios(Pageable pageable);

    @Operation(summary = "Cria uma nova assinatura para um usuário", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Assinaturas de Usuários"})
    @ApiResponse(responseCode = "201", description = "Assinatura criada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssinaturaUsuarioResponseDTO.class)))
    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    ResponseEntity<AssinaturaUsuarioResponseDTO> criarAssinaturaParaUsuario(@RequestBody @Valid AssinaturaUsuarioRequestDTO dto);

    @Operation(summary = "Cancela a assinatura de um usuário", security = @SecurityRequirement(name = "bearerAuth"), tags = {"Admin - Assinaturas de Usuários"})
    @ApiResponse(responseCode = "200", description = "Assinatura cancelada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssinaturaUsuarioResponseDTO.class)))
    @ApiResponse(responseCode = "403", description = "Acesso negado", content = @Content)
    @ApiResponse(responseCode = "404", description = "Assinatura não encontrada", content = @Content)
    ResponseEntity<AssinaturaUsuarioResponseDTO> cancelarAssinaturaDeUsuario(@Parameter(description = "ID da assinatura de usuário a ser cancelada") @PathVariable String id);
}