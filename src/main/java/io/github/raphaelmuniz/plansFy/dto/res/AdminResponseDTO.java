package io.github.raphaelmuniz.plansFy.dto.res;

import io.github.raphaelmuniz.plansFy.entities.Admin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponseDTO {
    private String id;
    private String nome;
    private String email;

    public AdminResponseDTO(Admin admin) {
        this.id = admin.getId();
        this.nome = admin.getNome();
        this.email = admin.getEmail();
    }
}