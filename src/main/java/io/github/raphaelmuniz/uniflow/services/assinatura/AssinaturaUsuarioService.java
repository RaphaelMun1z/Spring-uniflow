package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaUsuarioRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.profile.AssinaturaProfileResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaUsuarioResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.enums.StatusAssinaturaUsuarioEnum;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaUsuarioRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        List<StatusAssinaturaUsuarioEnum> statusVigentes = StatusAssinaturaUsuarioEnum.getStatusVigentes();
        Optional<AssinaturaUsuario> assinaturaExistente = assinaturaUsuarioRepository
                .findFirstVigenteByAssinanteId(data.getAssinanteId(), statusVigentes, LocalDateTime.now());
        if (assinaturaExistente.isPresent()) {
            throw new BusinessException("O usuário já possui uma assinatura ativa ou em teste. Cancele a assinatura atual para poder criar uma nova.");
        }

        AssinaturaModelo assinaturaModelo = assinaturaModeloRepository.findById(data.getAssinaturaModeloId()).orElseThrow(() -> new NotFoundException("Assinatura modelo não encontrada"));
        Assinante assinante = assinanteRepository.findById(data.getAssinanteId()).orElseThrow(() -> new NotFoundException("Assinante não encontrado"));
        AssinaturaUsuario assinaturaUsuario = new AssinaturaUsuario(null, null, LocalDateTime.now().plusMonths(1), data.getStatusAssinaturaUsuario(), assinaturaModelo, assinante, null);
        AssinaturaUsuario saved = repository.save(assinaturaUsuario);
        return new AssinaturaUsuarioResponseDTO(saved);
    }

    public List<AssinaturaProfileResponseDTO> findByAssinanteId(String assinanteId){
        List<AssinaturaUsuario> assinaturaUsuario = assinaturaUsuarioRepository.findByAssinanteId(assinanteId);
        List<AssinaturaProfileResponseDTO> assinaturasDto = assinaturaUsuario.stream().map(au -> {
            AssinaturaProfileResponseDTO responseDTO = new AssinaturaProfileResponseDTO();
            responseDTO.setAssinaturaUsuarioId(au.getId());
            responseDTO.setDataInicio(au.getDataInicio());
            responseDTO.setDataExpiracao(au.getDataExpiracao());
            responseDTO.setStatus(au.getStatus());
            AssinaturaModeloResponseDTO assinaturaModeloResponseDTO = new AssinaturaModeloResponseDTO(au.getAssinaturaModelo());
            responseDTO.setAssinaturaModelo(assinaturaModeloResponseDTO);
            return responseDTO;
        }).toList();
        return assinaturasDto;
    }
}
