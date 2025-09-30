package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.dto.req.TransmissaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.TransmissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Transmissao;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.NotificacaoRepository;
import io.github.raphaelmuniz.uniflow.repositories.TransmissaoDeNotificacaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
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
        return new TransmissaoResponseDTO(transmissoesSaved.getFirst());
    }
}
