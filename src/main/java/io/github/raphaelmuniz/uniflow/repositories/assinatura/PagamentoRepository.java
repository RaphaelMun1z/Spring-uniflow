package io.github.raphaelmuniz.uniflow.repositories.assinatura;

import io.github.raphaelmuniz.uniflow.entities.assinatura.Pagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, String> {
    Page<Pagamento> findByAssinantePagadorIdOrderByDataPagamentoDesc(String assinanteId, Pageable pageable);
}
