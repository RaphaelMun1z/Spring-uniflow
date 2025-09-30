package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.NotificacaoResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.*;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssinanteService extends GenericCrudServiceImpl<AssinanteRequestDTO, AssinanteResponseDTO, Assinante, String> {
    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    @Autowired
    AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    protected AssinanteService(AssinanteRepository repository) {
        super(repository, null, AssinanteResponseDTO::new);
    }

    @Override
    public AssinanteResponseDTO create(AssinanteRequestDTO data) {
        throw new IllegalArgumentException();
    }

    public List<GruposProfileResponseDTO> findGruposByAssinante(String assinanteId) {
        List<InscricaoGrupo> inscricoes = inscricaoGrupoRepository.findAllByMembro_Id(assinanteId);
        return inscricoes.stream().map(GruposProfileResponseDTO::new).toList();
    }

    public AssinaturaUsuarioResponseDTO getAssinaturaVigente(String assinanteId) {
        if (!repository.existsById(assinanteId)) {
            throw new NotFoundException("Assinante não encontrado.");
        }

        AssinaturaUsuario assinaturaVigente = assinaturaUsuarioRepository
                .findFirstByAssinanteIdAndStatusIsTrueAndDataExpiracaoAfter(assinanteId, LocalDateTime.now())
                .orElseThrow(() -> new NotFoundException("Nenhuma assinatura vigente encontrada para este assinante."));

        return new AssinaturaUsuarioResponseDTO(assinaturaVigente);
    }
}
