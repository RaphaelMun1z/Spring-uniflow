package io.github.raphaelmuniz.uniflow.repositories.autorizacao;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PapelRepository extends JpaRepository<Papel, String> {
    Optional<Papel> findByNome(String nome);
}
