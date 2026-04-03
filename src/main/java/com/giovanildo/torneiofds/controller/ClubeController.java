package com.giovanildo.torneiofds.controller;

import com.giovanildo.torneiofds.dto.ClubeRequest;
import com.giovanildo.torneiofds.dto.ClubeResponse;
import com.giovanildo.torneiofds.model.Clube;
import com.giovanildo.torneiofds.service.ClubeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clubes")
@RequiredArgsConstructor
@Tag(name = "Clubes", description = "Gerenciamento de clubes")
public class ClubeController {

    private final ClubeService clubeService;

    @GetMapping
    @Operation(summary = "Listar todos os clubes")
    public List<ClubeResponse> listar() {
        return clubeService.listarTodos().stream()
                .map(ClubeResponse::from)
                .toList();
    }

    @PostMapping
    @Operation(summary = "Cadastrar novo clube")
    @ResponseStatus(HttpStatus.CREATED)
    public ClubeResponse criar(@Valid @RequestBody ClubeRequest request) {
        Clube clube = new Clube(request.nome(), request.nacionalidade());
        return ClubeResponse.from(clubeService.salvar(clube));
    }
}
