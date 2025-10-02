package io.github.raphaelmuniz.uniflow.dto.res.grupo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AtividadeDoGrupoResponseDTO {
    private String id;
    private String titulo;
    private String tipo;

    private ResumoProfessor resumoProfessor;

    private StatusAluno statusAluno;

    private ResumoColaborativo resumoColaborativo;

    public record ResumoProfessor(int entregasFeitas, int totalAlunos) {}
    public record StatusAluno(String status, String nota) {}
    public record ResumoColaborativo(int membrosConcluiram, int totalMembros) {}
}
