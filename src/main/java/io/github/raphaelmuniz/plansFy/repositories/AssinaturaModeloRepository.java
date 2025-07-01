package io.github.raphaelmuniz.plansFy.repositories;

import io.github.raphaelmuniz.plansFy.entities.AssinaturaModelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssinaturaModeloRepository extends JpaRepository<AssinaturaModelo, String> {
    List<AssinaturaModelo> findByAtivo(Boolean status);
}
