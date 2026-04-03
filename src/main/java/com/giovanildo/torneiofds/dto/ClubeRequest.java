package com.giovanildo.torneiofds.dto;

import jakarta.validation.constraints.NotBlank;

public record ClubeRequest(
        @NotBlank String nome,
        @NotBlank String nacionalidade
) {}
