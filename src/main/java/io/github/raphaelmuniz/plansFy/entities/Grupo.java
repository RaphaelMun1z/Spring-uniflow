package io.github.raphaelmuniz.plansFy.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grupo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotNull
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private List<AtividadeCopia> atividades = new ArrayList<>();

    @NotNull
    @OneToMany(mappedBy = "grupo", cascade = CascadeType.ALL)
    private Set<InscricaoGrupo> inscritos = new HashSet<>();
}
