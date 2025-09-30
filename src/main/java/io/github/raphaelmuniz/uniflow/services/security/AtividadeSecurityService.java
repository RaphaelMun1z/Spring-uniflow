package io.github.raphaelmuniz.uniflow.services.security;

import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.repositories.AtividadeEntregaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtividadeSecurityService {
    @Autowired
    private AtividadeEntregaRepository atividadeEntregaRepository;

    @Transactional(readOnly = true)
    public boolean podeAvaliar(Authentication authentication, String atividadeId) {
        Usuario professorLogado = (Usuario) authentication.getPrincipal();
        return atividadeEntregaRepository.isProfessorCriadorDoGrupoDaAtividade(atividadeId, professorLogado.getId());
    }
}
