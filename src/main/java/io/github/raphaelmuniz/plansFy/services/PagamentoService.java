package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.PagamentoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Pagamento;
import io.github.raphaelmuniz.plansFy.repositories.PagamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService extends GenericCrudServiceImpl<PagamentoRequestDTO, PagamentoResponseDTO, Pagamento, String> {
    public PagamentoService(
            PagamentoRepository repository,
            PagamentoMapper pagamentoMapper
    ) {
        super(
                repository,
                pagamentoMapper::toEntity,
                PagamentoResponseDTO::new,
                null
        );
    }
}
