package io.github.raphaelmuniz.uniflow.services.assinatura;

import io.github.raphaelmuniz.uniflow.dto.req.assinatura.PagamentoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.assinatura.PagamentoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.assinatura.Pagamento;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.AssinanteRepository;
import io.github.raphaelmuniz.uniflow.repositories.assinatura.PagamentoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService extends GenericCrudServiceImpl<PagamentoRequestDTO, PagamentoResponseDTO, Pagamento, String> {
    @Autowired
    PagamentoRepository repository;

    @Autowired
    AssinanteRepository assinanteRepository;

    protected PagamentoService(PagamentoRepository repository) {
        super(repository, PagamentoRequestDTO::toModel, PagamentoResponseDTO::new);
    }

    @Override
    public PagamentoResponseDTO create(PagamentoRequestDTO data) {
        String assinanteId = data.getAssinanteId();
        Assinante assinante = assinanteRepository.findById(assinanteId).orElseThrow(() -> new NotFoundException("Assinante não encontrado"));

        Pagamento pagamento = data.toModel();
        pagamento.setAssinantePagador(assinante);

        Pagamento saved = repository.save(pagamento);

        return new PagamentoResponseDTO(saved);
    }

    public Page<PagamentoResponseDTO> listarHistoricoDePagamentos(String assinanteId, Pageable pageable) {
        if (!assinanteRepository.existsById(assinanteId)) {
            throw new NotFoundException("Assinante não encontrado.");
        }

        Page<Pagamento> pagamentos = repository.findByAssinantePagadorIdOrderByDataPagamentoDesc(assinanteId, pageable);
        return pagamentos.map(PagamentoResponseDTO::new);
    }
}
