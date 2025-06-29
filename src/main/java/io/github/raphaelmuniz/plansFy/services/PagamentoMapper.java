package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.PagamentoRequestDTO;
import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.entities.Pagamento;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.AssinanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PagamentoMapper {
    @Autowired
    AssinanteRepository assinanteRepository;

    public Pagamento toEntity(PagamentoRequestDTO reqDto) {
        String assinanteId = reqDto.getAssinanteId();
        Assinante assinante = assinanteRepository.findById(assinanteId)
                .orElseThrow(() -> new NotFoundException("Assinante não encontrado"));

        Pagamento pagamento = reqDto.toModel();
        pagamento.setAssinante(assinante);
        return pagamento;
    }
}
