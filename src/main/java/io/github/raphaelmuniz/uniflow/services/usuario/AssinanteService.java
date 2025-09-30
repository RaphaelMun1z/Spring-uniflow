package io.github.raphaelmuniz.uniflow.services.usuario;

import io.github.raphaelmuniz.uniflow.dto.req.usuario.AssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.GruposProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.repositories.grupo.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        List<InscricaoGrupo> inscricoes = inscricaoGrupoRepository.findAllByEstudanteMembro_Id(assinanteId);
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
