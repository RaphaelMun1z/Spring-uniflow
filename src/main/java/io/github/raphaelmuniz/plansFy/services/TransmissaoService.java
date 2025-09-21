package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.TransmissaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.TransmissaoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import io.github.raphaelmuniz.plansFy.entities.Transmissao;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.NotificacaoRepository;
import io.github.raphaelmuniz.plansFy.repositories.TransmissaoDeNotificacaoRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransmissaoService extends GenericCrudServiceImpl<TransmissaoRequestDTO, TransmissaoResponseDTO, Transmissao, String> {
    @Autowired
    TransmissaoDeNotificacaoRepository repository;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    protected TransmissaoService(TransmissaoDeNotificacaoRepository repository) {
        super(repository, TransmissaoRequestDTO::toModel, TransmissaoResponseDTO::new);
    }

    @Override
    public TransmissaoResponseDTO create(TransmissaoRequestDTO data) {
        List<Notificacao> notificacoes = notificacaoRepository.findAllById(data.getNotificacoesId());
        if (notificacoes.size() != data.getNotificacoesId().size()) {
            throw new NotFoundException("Alguma notificação não encontrada.");
        }

        List<Transmissao> novasTransmissoes = notificacoes.stream()
                .map(notificacao -> new Transmissao(
                        null,
                        notificacao,
                        data.getRemetenteTipo(),
                        data.getDestinatarioTipo(),
                        null,
                        "ENVIADA"
                ))
                .collect(Collectors.toList());

        List<Transmissao> transmissoesSaved = repository.saveAll(novasTransmissoes);
        //return transmissoesSaved.stream()
        //        .map(TransmissaoResponseDTO::new)
        //        .collect(Collectors.toList());
        return new TransmissaoResponseDTO(transmissoesSaved.getFirst());
    }
}
