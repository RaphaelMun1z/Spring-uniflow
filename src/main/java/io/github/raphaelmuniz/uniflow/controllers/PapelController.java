package io.github.raphaelmuniz.uniflow.controllers;

import io.github.raphaelmuniz.uniflow.controllers.generic.GenericCrudControllerImpl;
import io.github.raphaelmuniz.uniflow.dto.req.DisciplinaRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.req.PapelRequestDTO;
import io.github.raphaelmuniz.uniflow.dto.res.DisciplinaResponseDTO;
import io.github.raphaelmuniz.uniflow.dto.res.PapelResponseDTO;
import io.github.raphaelmuniz.uniflow.services.DisciplinaService;
import io.github.raphaelmuniz.uniflow.services.PapelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/papeis")
public class PapelController extends GenericCrudControllerImpl<PapelRequestDTO, PapelResponseDTO> {
    protected PapelController(PapelService service) {
        super(service);
    }

//    @GetMapping("/whoami")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<String> whoAmI() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        if (authentication == null) {
//            return ResponseEntity.status(401).body("Nenhum usuário autenticado.");
//        }
//
//        String username = authentication.getName();
//        String authorities = authentication.getAuthorities().stream()
//                .map(grantedAuthority -> grantedAuthority.getAuthority())
//                .collect(Collectors.joining(", "));
//
//        String response = "Usuário: " + username + " | Permissões: [" + authorities + "]";
//
//        return ResponseEntity.ok(response);
//    }
}
