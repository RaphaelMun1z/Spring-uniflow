package io.github.raphaelmuniz.uniflow.services.autorizacao;

public interface EmailService {
    void enviarEmailRedefinicaoSenha(String para, String token);
}