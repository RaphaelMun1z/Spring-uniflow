package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.Assinante;
import io.github.raphaelmuniz.uniflow.entities.Pagamento;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.PagamentoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService extends GenericCrudServiceImpl<PagamentoRequestDTO, PagamentoResponseDTO, Pagamento, String> {
    @Autowired
    AssinanteRepository assinanteRepository;

    protected PagamentoService(PagamentoRepository repository) {
        super(repository, PagamentoRequestDTO::toModel, PagamentoResponseDTO::new);
    }

    @Override
    public PagamentoResponseDTO create(PagamentoRequestDTO data) {
        String assinanteId = data.getAssinanteId();
        Assinante assinante = assinanteRepository.findById(assinanteId)
                .orElseThrow(() -> new NotFoundException("Assinante não encontrado"));

        Pagamento pagamento = data.toModel();
        pagamento.setAssinante(assinante);

        Pagamento saved = repository.save(pagamento);

        return new PagamentoResponseDTO(saved);
    }
}
