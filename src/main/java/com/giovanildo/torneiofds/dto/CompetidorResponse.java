package com.giovanildo.torneiofds.dto;

import com.giovanildo.torneiofds.model.Competidor;

public record CompetidorResponse(
        Long id,
        String nomeEAtleta,
        String nomeClube
) {
    public static CompetidorResponse from(Competidor c) {
        return new CompetidorResponse(
                c.getId(),
                c.getEAtleta().getNome(),
                c.getClube().getNome()
        );
    }
}
