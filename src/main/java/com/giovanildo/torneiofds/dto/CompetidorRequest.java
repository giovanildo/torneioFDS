package com.giovanildo.torneiofds.dto;

import jakarta.validation.constraints.NotNull;

public record CompetidorRequest(
        @NotNull Long eAtletaId,
        @NotNull Long clubeId
) {}
