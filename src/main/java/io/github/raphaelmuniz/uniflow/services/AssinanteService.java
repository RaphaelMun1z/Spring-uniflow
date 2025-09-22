package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AssinanteRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Assinante;
import io.github.raphaelmuniz.uniflow.entities.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.repositories.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.InscricaoGrupoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssinanteService extends GenericCrudServiceImpl<AssinanteRequestDTO, AssinanteResponseDTO, Assinante, String> {
    @Autowired
    InscricaoGrupoRepository inscricaoGrupoRepository;

    protected AssinanteService(AssinanteRepository repository) {
        super(repository, null, AssinanteResponseDTO::new);
    }

    @Override
    public AssinanteResponseDTO create(AssinanteRequestDTO data) {
        throw new IllegalArgumentException();
    }

    public List<GrupoResponseDTO> findGruposByAssinante(String assinanteId) {
        List<InscricaoGrupo> inscricoes = inscricaoGrupoRepository.findAllByInscrito_Id(assinanteId);
        return inscricoes.stream()
                .map(InscricaoGrupo::getGrupo)
                .map(GrupoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
