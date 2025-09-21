package io.github.raphaelmuniz.plansFy.repositories;

import io.github.raphaelmuniz.plansFy.entities.Transmissao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmissaoDeNotificacaoRepository extends JpaRepository<Transmissao, String> {
}
