package io.github.raphaelmuniz.uniflow.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Grupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank(message = "Título não pode ser vazio/nulo")
    private String titulo;

    @NotBlank(message = "Descrição não pode ser vazio/nulo")
    private String descricao;

    @NotNull(message = "Atividades Publicadas não pode ser nulo")
    @OneToMany(mappedBy = "grupoPublicado", cascade = CascadeType.ALL)
    private List<AtividadeGrupo> atividadesPublicadas = new ArrayList<>();

    @NotNull(message = "Inscrições não pode ser nulo")
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> inscricoes = new HashSet<>();
}
