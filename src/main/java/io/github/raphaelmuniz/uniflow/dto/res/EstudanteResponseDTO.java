package io.github.raphaelmuniz.uniflow.dto.res;

import io.github.raphaelmuniz.uniflow.entities.assinatura.AssinaturaUsuario;
import io.github.raphaelmuniz.uniflow.entities.atividade.AtividadeEntrega;
import io.github.raphaelmuniz.uniflow.entities.grupo.InscricaoGrupo;
import io.github.raphaelmuniz.uniflow.entities.usuario.Estudante;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EstudanteResponseDTO {
    private String id;
    private String nome;
    private String email;
    private Integer periodo;
    private String assinaturaId;
    private List<String> atividadesId;
    private List<String> inscricoesGrupoId;

    public EstudanteResponseDTO(Estudante estudante) {
        this.id = estudante.getId();
        this.nome = estudante.getNome();
        this.email = estudante.getEmail();
        this.periodo = estudante.getPeriodo();
        this.assinaturaId = estudante.getAssinaturaValida().map(AssinaturaUsuario::getId).orElse(null);
        this.atividadesId = estudante.getAtividadesEstudante().stream().map(AtividadeEntrega::getId).toList();
        this.inscricoesGrupoId = estudante.getInscricoesGrupos().stream().map(InscricaoGrupo::getId).toList();
    }

    public Estudante toModel() {
        return new Estudante(nome, email, null, null, null, null, null, null, null, null, this.periodo, null);
    }


}
