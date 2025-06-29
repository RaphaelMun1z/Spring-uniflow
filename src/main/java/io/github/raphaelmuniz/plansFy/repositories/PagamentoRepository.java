package io.github.raphaelmuniz.plansFy.repositories;

import io.github.raphaelmuniz.plansFy.entities.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, String> {
}
