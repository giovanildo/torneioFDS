package com.giovanildo.torneiofds.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tab_torneio")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Torneio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do torneio e obrigatorio")
    @Column(nullable = false)
    private String nome;

    private String porqueDoNome;

    @Column(name = "data_torneio", nullable = false)
    private LocalDate dataTorneio = LocalDate.now();

    @OneToMany(mappedBy = "torneio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Competidor> competidores = new ArrayList<>();

    public Torneio(String nome, String porqueDoNome) {
        this.nome = nome;
        this.porqueDoNome = porqueDoNome;
        this.dataTorneio = LocalDate.now();
    }

    @Override
    public String toString() {
        return nome + "  " + porqueDoNome;
    }
}
