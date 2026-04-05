package com.giovanildo.torneiofds.dto;

import java.util.List;

public record ConfrontoResponse(
        String nomeJogador1,
        String nomeJogador2,
        int vitoriasJogador1,
        int vitoriasJogador2,
        int empates,
        int golsJogador1,
        int golsJogador2,
        List<PartidaResponse> partidas
) {}
