package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.TransmissaoDeNotificacaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.TransmissaoDeNotificacaoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.TransmissaoDeNotificacao;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.TransmissaoDeNotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransmissaoDeNotificacaoService {
    @Autowired
    TransmissaoDeNotificacaoRepository repository;

    @Transactional
    public TransmissaoDeNotificacaoResponseDTO create(TransmissaoDeNotificacaoRequestDTO data) {
        TransmissaoDeNotificacao saved = repository.save(data.toModel());
        return new TransmissaoDeNotificacaoResponseDTO(saved);
    }

    public List<TransmissaoDeNotificacaoResponseDTO> findAll() {
        return repository.findAll().stream().map(TransmissaoDeNotificacaoResponseDTO::new).collect(Collectors.toList());
    }

    public TransmissaoDeNotificacaoResponseDTO findById(String id) {
        TransmissaoDeNotificacao found = repository.findById(id).orElseThrow(() -> new NotFoundException("Transmissão de notificação não encontrada."));
        return new TransmissaoDeNotificacaoResponseDTO(found);
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

}
