package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import io.github.raphaelmuniz.plansFy.entities.AtividadeModelo;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinaturaModeloRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
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
