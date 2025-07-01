package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.plansFy.dto.req.AdicionarMembrosGrupoDTO;
import io.github.raphaelmuniz.plansFy.dto.req.GrupoRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.GrupoResponseDTO;
import io.github.raphaelmuniz.plansFy.services.GrupoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController extends GenericCrudControllerImpl<GrupoRequestDTO, GrupoResponseDTO> {
    @Autowired
    GrupoService service;

    protected GrupoController(GrupoService service) {
        super(service);
    }

    @PostMapping("/adicionar/membros")
    public ResponseEntity<Void> adicionarMembro(@RequestBody @Valid AdicionarMembrosGrupoDTO data) {
        service.adicionarIntegrantes(data);
        return ResponseEntity.ok().build();
    }
//
//    @DeleteMapping("/remover/aluno")
//    public ResponseEntity<Void> removerAluno(@RequestBody @Valid EstudanteEmGrupoRequestDTO data) {
//        service.removerAluno(data);
//        return ResponseEntity.ok().build();
//    }
}
