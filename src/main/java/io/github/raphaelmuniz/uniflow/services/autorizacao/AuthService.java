package io.github.raphaelmuniz.uniflow.services.autorizacao;

import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.EsquecerSenhaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.RedefinirSenhaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.autorizacao.SignUpRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.usuario.AssinanteResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.security.AccountCredentialsDTO;
import io.github.raphaelmuniz.uniflow.dto.security.TokenDTO;
import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import io.github.raphaelmuniz.uniflow.exceptions.models.BusinessException;
import io.github.raphaelmuniz.uniflow.repositories.usuario.UsuarioRepository;
import io.github.raphaelmuniz.uniflow.security.jwt.JwtTokenProvider;
import io.github.raphaelmuniz.uniflow.services.validation.usuario.UsuarioFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioFactory usuarioFactory;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtTokenProvider tokenProvider,
            UsuarioRepository usuarioRepository,
            UsuarioFactory usuarioFactory,
            PasswordEncoder passwordEncoder,
            EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.usuarioRepository = usuarioRepository;
        this.usuarioFactory = usuarioFactory;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public TokenDTO signin(AccountCredentialsDTO credentials) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(credentials.email(), credentials.senha());
            var auth = authenticationManager.authenticate(usernamePassword);
            var user = (Usuario) auth.getPrincipal();

            return tokenProvider.createAccessToken(
                    user.getEmail(),
                    user.getAuthorities()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadCredentialsException("Invalid username/password supplied!");
        }
    }

    public TokenDTO signin(String email, String refreshToken) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("E-mail " + email + " não encontrado!");
        }

        return tokenProvider.refreshToken(refreshToken);
    }

    @Transactional
    public AssinanteResponseDTO signup(SignUpRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()).isPresent()) {
            throw new BusinessException("O email informado já está em uso.");
        }

        Usuario novoUsuario = usuarioFactory.criarUsuario(dto);

        Usuario usuarioSalvo = usuarioRepository.save(novoUsuario);

        return AssinanteResponseDTO.fromEntity((Assinante) usuarioSalvo);
    }

    @Transactional
    public void esquecerSenha(EsquecerSenhaRequestDTO dto) {
        usuarioRepository.findByEmail(dto.email()).ifPresent(usuario -> {
            String token = UUID.randomUUID().toString();
            usuario.setPasswordResetToken(token);
            usuario.setPasswordResetTokenExpiry(LocalDateTime.now().plusHours(1));
            usuarioRepository.save(usuario);

            emailService.enviarEmailRedefinicaoSenha(usuario.getEmail(), token);
        });
    }

    @Transactional
    public void redefinirSenha(RedefinirSenhaRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByPasswordResetToken(dto.token())
                .orElseThrow(() -> new BusinessException("Token de redefinição de senha inválido ou já utilizado."));

        if (usuario.getPasswordResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Token de redefinição de senha expirado.");
        }

        usuario.setSenha(passwordEncoder.encode(dto.novaSenha()));
        usuario.setPasswordResetToken(null);
        usuario.setPasswordResetTokenExpiry(null);

        usuarioRepository.save(usuario);
    }
}
