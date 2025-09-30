package io.github.raphaelmuniz.uniflow.services.notificacao;

import io.github.raphaelmuniz.uniflow.dto.req.notificacao.TransmissaoRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.notificacao.TransmissaoResponseDTO;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Notificacao;
import io.github.raphaelmuniz.uniflow.entities.notificacao.Transmissao;
import io.github.raphaelmuniz.uniflow.exceptions.models.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.NotificacaoRepository;
import io.github.raphaelmuniz.uniflow.repositories.notificacao.TransmissaoRepository;
import io.github.raphaelmuniz.uniflow.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransmissaoService extends GenericCrudServiceImpl<TransmissaoRequestDTO, TransmissaoResponseDTO, Transmissao, String> {
    @Autowired
    TransmissaoRepository repository;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    protected TransmissaoService(TransmissaoRepository repository) {
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
