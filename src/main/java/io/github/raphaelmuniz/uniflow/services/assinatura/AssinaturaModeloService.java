package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.AssinaturaModeloRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.AssinaturaModeloResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.AssinaturaModeloRepository;
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
        super(repository, AssinaturaModeloResponseDTO::new);
    }

    @Transactional
    public void alterarStatus(String assinaturaModeloId, Boolean novoStatus) {
        AssinaturaModelo assinaturaModelo = repository.findById(assinaturaModeloId).orElseThrow(() -> new NotFoundException("Assinatura modelo não encontrada."));
        assinaturaModelo.setAtivo(novoStatus);
        repository.save(assinaturaModelo);
    }

    public List<AssinaturaModelo> listarAssinaturasModeloAtivas() {
        return repository.findByAtivo(true);
    }

}
