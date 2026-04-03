package com.giovanildo.torneiofds.dto;

import com.giovanildo.torneiofds.model.EAtleta;

public record EAtletaResponse(
        Long id,
        String nome,
        String login
) {
    public static EAtletaResponse from(EAtleta e) {
        return new EAtletaResponse(e.getId(), e.getNome(), e.getLogin());
    }
}
