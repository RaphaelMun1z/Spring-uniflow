package io.github.raphaelmuniz.uniflow.repositories.usuario;

import io.github.raphaelmuniz.uniflow.entities.usuario.Assinante;
import io.github.raphaelmuniz.uniflow.entities.usuario.Usuario;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    @Query("SELECT u FROM Usuario u JOIN FETCH u.papel p JOIN FETCH p.permissoes WHERE u.email = :email")
    Optional<Usuario> findByEmailWithRolesAndPermissions(@Param("email") String email);

    Optional<Usuario> findByEmail(String email);

    @Query("SELECT u FROM Usuario u WHERE TYPE(u) IN (Professor, Estudante)")
    Page<Assinante> findAllAssinantes(Pageable pageable);

    Optional<Usuario> findByPasswordResetToken(@NotBlank(message = "O token de redefinição é obrigatório.") String token);

    boolean existsByPapel_Id(String id);

    @Query("SELECT u FROM Usuario u WHERE TYPE(u) = :tipo")
    Page<Assinante> findAllByTipo(@Param("tipo") Class<? extends Assinante> tipo, Pageable pageable);}
