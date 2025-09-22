package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.AssinaturaModeloRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssinaturaModeloService extends GenericCrudServiceImpl<AssinaturaModeloRequestDTO, AssinaturaModeloResponseDTO, AssinaturaModelo, String> {
    @Autowired
    AssinaturaModeloRepository repository;

    protected AssinaturaModeloService(AssinaturaModeloRepository repository) {
        super(repository, AssinaturaModeloRequestDTO::toModel, AssinaturaModeloResponseDTO::new);
    }

    @Transactional
    public void alterarStatus(String assinaturaModeloId, Boolean novoStatus) {
        AssinaturaModelo assinaturaModelo = repository.findById(assinaturaModeloId).orElseThrow(() -> new NotFoundException("Assinatura modelo não encontrada."));
        assinaturaModelo.setAtivo(novoStatus);
        repository.save(assinaturaModelo);
    }

    public List<AssinaturaModelo> listarAssinaturasAtivas() {
        List<AssinaturaModelo> ativos = repository.findByAtivo(true);
        return ativos;
    }

}
