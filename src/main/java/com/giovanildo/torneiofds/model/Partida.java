package com.giovanildo.torneiofds.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tab_partida")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "torneio_id")
    private Torneio torneio;

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetidorEmCampo> competidoresEmCampo = new ArrayList<>();

    private boolean encerrada = false;

    private int rodada;

    @Transient
    public CompetidorEmCampo getAnfitriao() {
        return competidoresEmCampo.stream()
                .filter(CompetidorEmCampo::isJogaEmCasa)
                .findFirst()
                .orElse(null);
    }

    @Transient
    public CompetidorEmCampo getVisitante() {
        return competidoresEmCampo.stream()
                .filter(c -> !c.isJogaEmCasa())
                .findFirst()
                .orElse(null);
    }
}
