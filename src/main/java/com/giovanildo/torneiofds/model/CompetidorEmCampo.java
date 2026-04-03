package com.giovanildo.torneiofds.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tab_competidor_em_campo")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CompetidorEmCampo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "competidor_id")
    private Competidor competidor;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partida_id")
    private Partida partida;

    private int gols = 0;

    private boolean jogaEmCasa;

    public CompetidorEmCampo(Partida partida, Competidor competidor, boolean jogaEmCasa) {
        this.partida = partida;
        this.competidor = competidor;
        this.jogaEmCasa = jogaEmCasa;
    }
}
