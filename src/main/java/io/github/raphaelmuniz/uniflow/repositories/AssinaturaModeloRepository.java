package io.github.raphaelmuniz.uniflow.repositories;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssinaturaModeloRepository extends JpaRepository<AssinaturaModelo, String> {
    List<AssinaturaModelo> findByAtivo(Boolean status);
}
