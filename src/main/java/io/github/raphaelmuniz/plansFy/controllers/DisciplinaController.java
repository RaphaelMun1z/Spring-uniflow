package io.github.raphaelmuniz.plansFy.controllers;

import io.github.raphaelmuniz.plansFy.dto.req.DisciplinaRequestDTO;
import io.github.raphaelmuniz.plansFy.dto.res.AtividadeModeloResponseDTO;
import io.github.raphaelmuniz.plansFy.dto.res.DisciplinaResponseDTO;
import io.github.raphaelmuniz.plansFy.services.DisciplinaService;
import io.github.raphaelmuniz.plansFy.services.interfaces.CrudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplinas")
public class DisciplinaController extends GenericCrudControllerImpl<DisciplinaRequestDTO, DisciplinaResponseDTO> {
    protected DisciplinaController(DisciplinaService service) {
        super(service);
    }

//    @GetMapping("/{id}/atividades")
//    public ResponseEntity<List<AtividadeModeloResponseDTO>> findAll(@PathVariable String id) {
//        return ResponseEntity.ok(service.listarAtividadesDisciplina(id));
//    }
}
