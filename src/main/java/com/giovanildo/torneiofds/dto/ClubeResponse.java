package com.giovanildo.torneiofds.dto;

import com.giovanildo.torneiofds.model.Clube;

public record ClubeResponse(
        Long id,
        String nome,
        String nacionalidade
) {
    public static ClubeResponse from(Clube c) {
        return new ClubeResponse(c.getId(), c.getNome(), c.getNacionalidade());
    }
}
