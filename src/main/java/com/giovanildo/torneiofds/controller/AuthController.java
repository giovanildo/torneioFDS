package com.giovanildo.torneiofds.controller;

import com.giovanildo.torneiofds.dto.EAtletaResponse;
import com.giovanildo.torneiofds.dto.LoginRequest;
import com.giovanildo.torneiofds.dto.RegistroRequest;
import com.giovanildo.torneiofds.model.EAtleta;
import com.giovanildo.torneiofds.service.EAtletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacao", description = "Login e registro de jogadores")
public class AuthController {

    private final EAtletaService eAtletaService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @Operation(summary = "Fazer login")
    public ResponseEntity<EAtletaResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.senha())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        EAtleta eAtleta = eAtletaService.buscarPorLogin(request.login());
        return ResponseEntity.ok(EAtletaResponse.from(eAtleta));
    }

    @PostMapping("/registrar")
    @Operation(summary = "Criar nova conta de jogador")
    @ResponseStatus(HttpStatus.CREATED)
    public EAtletaResponse registrar(@Valid @RequestBody RegistroRequest request) {
        EAtleta eAtleta = eAtletaService.registrar(request.nome(), request.login(), request.senha());
        return EAtletaResponse.from(eAtleta);
    }
}
