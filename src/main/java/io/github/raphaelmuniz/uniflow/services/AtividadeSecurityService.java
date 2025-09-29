package io.github.raphaelmuniz.uniflow.services;

import io.github.raphaelmuniz.uniflow.entities.AtividadeAssinante;
import io.github.raphaelmuniz.uniflow.entities.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.NotFoundException;
import io.github.raphaelmuniz.uniflow.repositories.AtividadeAssinanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtividadeSecurityService {
    @Autowired
    private AtividadeAssinanteRepository atividadeAssinanteRepository;

    @Transactional(readOnly = true)
    public boolean podeAvaliar(Authentication authentication, String atividadeId) {
        Usuario professorLogado = (Usuario) authentication.getPrincipal();
        return atividadeAssinanteRepository.isProfessorCriadorDoGrupoDaAtividade(atividadeId, professorLogado.getId());
    }
}
