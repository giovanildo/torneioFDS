package com.giovanildo.torneiofds.model;

import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Classificacao implements Comparable<Classificacao> {

    private String nomeEAtleta;
    private String nomeclube;
    private int pontos;
    private int jogos;
    private int vitorias;
    private int empates;
    private int derrotas;
    private int golsPro;
    private int golsContra;

    public int getSaldo() {
        return golsPro - golsContra;
    }

    public int getAproveitamento() {
        if (jogos == 0) return 0;
        return (pontos * 100) / (jogos * 3);
    }

    @Override
    public int compareTo(Classificacao outro) {
        int cmp = Integer.compare(outro.pontos, this.pontos);
        if (cmp != 0) return cmp;
        cmp = Integer.compare(outro.getVitorias(), this.getVitorias());
        if (cmp != 0) return cmp;
        return Integer.compare(outro.getSaldo(), this.getSaldo());
    }
}
