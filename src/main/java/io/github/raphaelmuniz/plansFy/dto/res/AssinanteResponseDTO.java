package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.Assinante;
import io.github.raphaelmuniz.plansFy.entities.AssinaturaUsuario;
import io.github.raphaelmuniz.plansFy.entities.AtividadeCopia;
import io.github.raphaelmuniz.plansFy.entities.InscricaoGrupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssinanteResponseDTO {
    private String id;
    private String nome;
    private String email;
    private AssinaturaUsuario assinatura;
    private List<AtividadeCopia> atividades;
    private Set<InscricaoGrupo> grupos;

    public AssinanteResponseDTO(Assinante assinante) {
        this.id = assinante.getId();
        this.nome = assinante.getNome();
        this.email = assinante.getEmail();
        this.assinatura = assinante.getAssinatura();
        this.atividades = assinante.getAtividades();
        this.grupos = assinante.getGrupos();
    }

}
