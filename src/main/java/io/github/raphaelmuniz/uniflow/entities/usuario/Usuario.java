package io.github.raphaelmuniz.uniflow.entities.usuario;

import io.github.raphaelmuniz.uniflow.entities.autorizacao.Papel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public abstract class Usuario implements UserDetails, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Nome não pode ser vazio/nulo")
    private String nome;

    @NotBlank(message = "E-mail não pode ser vazio/nulo")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Senha não pode ser vazio/nulo")
    private String senha;

    @NotNull(message = "Data cadastro não pode ser nulo.")
    private LocalDateTime dataCadastro;

    @ManyToOne
    @JoinColumn(name = "papel_id")
    private Papel papel;

    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;

    public Usuario(String nome, String email, String senha, Papel papel) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.papel = papel;
        this.isAccountNonExpired = true;
        this.isAccountNonLocked = true;
        this.isCredentialsNonExpired = true;
        this.isEnabled = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.papel != null) {
            return this.papel.getPermissoes();
        }
        return Set.of();
    }

    public boolean hasRole(String nomeDoPapel) {
        if (this.papel == null || nomeDoPapel == null) {
            return false;
        }
        return this.papel.getNome().equalsIgnoreCase(nomeDoPapel);
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
