package io.github.raphaelmuniz.plansFy.services;

import io.github.raphaelmuniz.plansFy.dto.req.TransmissaoDeNotificacaoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.TransmissaoDeNotificacaoResponseDTO;
import io.github.raphaelmuniz.plansFy.entities.Notificacao;
import io.github.raphaelmuniz.plansFy.entities.TransmissaoDeNotificacao;
import io.github.raphaelmuniz.plansFy.exceptions.NotFoundException;
import io.github.raphaelmuniz.plansFy.repositories.NotificacaoRepository;
import io.github.raphaelmuniz.plansFy.repositories.TransmissaoDeNotificacaoRepository;
import io.github.raphaelmuniz.plansFy.services.generic.GenericCrudServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmissaoDeNotificacaoService extends GenericCrudServiceImpl<TransmissaoDeNotificacaoRequestDTO, TransmissaoDeNotificacaoResponseDTO, TransmissaoDeNotificacao, String> {
    @Autowired
    TransmissaoDeNotificacaoRepository repository;

    @Autowired
    NotificacaoRepository notificacaoRepository;

    protected TransmissaoDeNotificacaoService(TransmissaoDeNotificacaoRepository repository) {
        super(repository, TransmissaoDeNotificacaoRequestDTO::toModel, TransmissaoDeNotificacaoResponseDTO::new);
    }

    @Override
    public TransmissaoDeNotificacaoResponseDTO create(TransmissaoDeNotificacaoRequestDTO data) {
        TransmissaoDeNotificacao tn = new TransmissaoDeNotificacao(null, data.getRemetenteTipo(), data.getRemetenteId(), data.getDestinatarioTipo(), data.getDestinatarioId(), null);

        List<Notificacao> notificacoes = notificacaoRepository.findAllById(data.getNotificacoesId());
        if(notificacoes.size() != data.getNotificacoesId().size()){
            throw new NotFoundException("Alguma notificação não encontrada.");
        }
        tn.setNotificacoes(notificacoes);

        TransmissaoDeNotificacao saved = repository.save(data.toModel());
        return new TransmissaoDeNotificacaoResponseDTO(saved);
    }
}
