package com.giovanildo.torneiofds.dto;

import com.giovanildo.torneiofds.model.Torneio;

import java.time.LocalDate;

public record TorneioResponse(
        Long id,
        String nome,
        String porqueDoNome,
        LocalDate dataTorneio,
        int totalCompetidores
) {
    public static TorneioResponse from(Torneio t) {
        return new TorneioResponse(
                t.getId(),
                t.getNome(),
                t.getPorqueDoNome(),
                t.getDataTorneio(),
                t.getCompetidores() != null ? t.getCompetidores().size() : 0
        );
    }
}
