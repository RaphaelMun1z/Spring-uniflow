package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Assinante;
import io.github.raphaelmuniz.uniflow.entities.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AssinaturaUsuarioService extends GenericCrudServiceImpl<AssinaturaUsuarioRequestDTO, AssinaturaUsuarioResponseDTO, AssinaturaUsuario, String> {
    @Autowired
    AssinaturaUsuarioRepository assinaturaUsuarioRepository;

    @Autowired
    AssinaturaModeloRepository assinaturaModeloRepository;

    @Autowired
    AssinanteRepository assinanteRepository;

    protected AssinaturaUsuarioService(AssinaturaUsuarioRepository repository) {
        super(repository, AssinaturaUsuarioRequestDTO::toModel, AssinaturaUsuarioResponseDTO::new);
    }

    @Override
    public AssinaturaUsuarioResponseDTO create(AssinaturaUsuarioRequestDTO data) {
        AssinaturaModelo assinaturaModelo = assinaturaModeloRepository.findById(data.getAssinaturaId()).orElseThrow(() -> new NotFoundException("Assinatura modelo não encontrada"));
        Assinante assinante = assinanteRepository.findById(data.getAssinanteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado"));
        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, assinaturaModelo, LocalDateTime.now(), LocalDateTime.now(), true, assinante);

        AssinaturaUsuario saved = repository.save(assinaturaUsuario);
        return new AssinaturaUsuarioResponseDTO(saved);
    }

    public AssinaturaProfileResponseDTO findByAssinanteId(String assinanteId){
        AssinaturaUsuario assinaturaUsuario = assinaturaUsuarioRepository.findByAssinanteId(assinanteId).orElseThrow(() -> new NotFoundException("Assinatura usuário não encontrada"));
        AssinaturaProfileResponseDTO responseDTO = new AssinaturaProfileResponseDTO();
        responseDTO.setAssinaturaId(assinaturaUsuario.getId());
        responseDTO.setDataInicio(assinaturaUsuario.getDataInicio());
        responseDTO.setDataExpiracao(assinaturaUsuario.getDataExpiracao());
        responseDTO.setStatus(assinaturaUsuario.getStatus());
        responseDTO.setAssinaturaModelo(new AssinaturaModeloResponseDTO(assinaturaUsuario.getAssinaturaModelo()));
        return responseDTO;
    }
}
