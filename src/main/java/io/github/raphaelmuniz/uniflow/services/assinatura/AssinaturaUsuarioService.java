package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        AssinaturaModelo assinaturaModelo = assinaturaModeloRepository.findById(data.getAssinaturaModeloId()).orElseThrow(() -> new NotFoundException("Assinatura modelo não encontrada"));
        Assinante assinante = assinanteRepository.findById(data.getAssinanteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado"));
        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, data.getDataInicio(), data.getDataExpiracao(), data.getStatusAssinaturaUsuario(), assinaturaModelo, assinante, null);
        AssinaturaUsuario saved = repository.save(assinaturaUsuario);
        return new AssinaturaUsuarioResponseDTO(saved);
    }

    public AssinaturaProfileResponseDTO findByAssinanteId(String assinanteId){
        AssinaturaUsuario assinaturaUsuario = assinaturaUsuarioRepository.findByAssinanteId(assinanteId).orElseThrow(() -> new NotFoundException("Assinatura usuário não encontrada"));
        AssinaturaProfileResponseDTO responseDTO = new AssinaturaProfileResponseDTO();
        responseDTO.setAssinaturaUsuarioId(assinaturaUsuario.getId());
        responseDTO.setDataInicio(assinaturaUsuario.getDataInicio());
        responseDTO.setDataExpiracao(assinaturaUsuario.getDataExpiracao());
        responseDTO.setStatus(assinaturaUsuario.getStatus());
        responseDTO.setAssinaturaModelo(new AssinaturaModeloResponseDTO(assinaturaUsuario.getAssinaturaModelo()));
        return responseDTO;
    }
}
