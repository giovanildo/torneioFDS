package com.giovanildo.torneiofds.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tab_competidor")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Competidor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "eatleta_id")
    private EAtleta eAtleta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "clube_id")
    private Clube clube;

    @ManyToOne(optional = false)
    @JoinColumn(name = "torneio_id")
    private Torneio torneio;

    public Competidor(Torneio torneio, EAtleta eAtleta, Clube clube) {
        this.torneio = torneio;
        this.eAtleta = eAtleta;
        this.clube = clube;
    }

    @Override
    public String toString() {
        return eAtleta + " - " + clube;
    }
}
