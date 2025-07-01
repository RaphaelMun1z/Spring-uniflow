package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.GrupoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos")
public class GrupoController extends GenericCrudControllerImpl<GrupoRequestDTO, GrupoResponseDTO> {
    protected GrupoController(GrupoService service) {
        super(service);
    }

//    @PostMapping("/adicionar/aluno")
//    public ResponseEntity<Void> adicionarAluno(@RequestBody @Valid EstudanteEmGrupoRequestDTO data) {
//        service.adicionarAluno(data);
//        return ResponseEntity.ok().build();
//    }
//
//    @DeleteMapping("/remover/aluno")
//    public ResponseEntity<Void> removerAluno(@RequestBody @Valid EstudanteEmGrupoRequestDTO data) {
//        service.removerAluno(data);
//        return ResponseEntity.ok().build();
//    }
}
