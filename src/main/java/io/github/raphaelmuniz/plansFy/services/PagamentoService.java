package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.entities.Pagamento;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinanteRepository;
import io.github.raphaelmuniz.plansFy.repositories.PagamentoRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
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
