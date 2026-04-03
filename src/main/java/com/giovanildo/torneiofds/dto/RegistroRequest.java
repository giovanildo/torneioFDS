package com.giovanildo.torneiofds.dto;

import jakarta.validation.constraints.NotBlank;

public record RegistroRequest(
        @NotBlank String nome,
        @NotBlank String login,
        @NotBlank String senha
) {}
