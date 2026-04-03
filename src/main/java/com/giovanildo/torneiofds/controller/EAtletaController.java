package com.giovanildo.torneiofds.controller;

import com.giovanildo.torneiofds.dto.EAtletaResponse;
import com.giovanildo.torneiofds.service.EAtletaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eatletas")
@RequiredArgsConstructor
@Tag(name = "EAtletas", description = "Jogadores de videogame")
public class EAtletaController {

    private final EAtletaService eAtletaService;

    @GetMapping
    @Operation(summary = "Listar todos os jogadores")
    public List<EAtletaResponse> listar() {
        return eAtletaService.listarTodos().stream()
                .map(EAtletaResponse::from)
                .toList();
    }
}
