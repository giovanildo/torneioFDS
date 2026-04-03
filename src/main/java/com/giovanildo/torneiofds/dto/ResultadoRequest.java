package com.giovanildo.torneiofds.dto;

import jakarta.validation.constraints.Min;

public record ResultadoRequest(
        @Min(0) int golsAnfitriao,
        @Min(0) int golsVisitante
) {}
