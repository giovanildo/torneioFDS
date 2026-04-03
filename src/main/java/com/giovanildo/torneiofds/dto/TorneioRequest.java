package com.giovanildo.torneiofds.dto;

import jakarta.validation.constraints.NotBlank;

public record TorneioRequest(
        @NotBlank String nome,
        String porqueDoNome
) {}
