package com.giovanildo.torneiofds.dto;

import com.giovanildo.torneiofds.model.CompetidorEmCampo;
import com.giovanildo.torneiofds.model.Partida;

public record PartidaResponse(
        Long id,
        int rodada,
        boolean encerrada,
        JogadorEmCampo anfitriao,
        JogadorEmCampo visitante
) {
    public record JogadorEmCampo(
            String nomeEAtleta,
            String nomeClube,
            int gols
    ) {
        public static JogadorEmCampo from(CompetidorEmCampo c) {
            if (c == null) return null;
            return new JogadorEmCampo(
                    c.getCompetidor().getEAtleta().getNome(),
                    c.getCompetidor().getClube().getNome(),
                    c.getGols()
            );
        }
    }

    public static PartidaResponse from(Partida p) {
        return new PartidaResponse(
                p.getId(),
                p.getRodada(),
                p.isEncerrada(),
                JogadorEmCampo.from(p.getAnfitriao()),
                JogadorEmCampo.from(p.getVisitante())
        );
    }
}
