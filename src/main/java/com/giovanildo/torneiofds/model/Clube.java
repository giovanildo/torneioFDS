package com.giovanildo.torneiofds.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tab_clube")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Clube {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do clube e obrigatorio")
    @Column(length = 60, nullable = false)
    private String nome;

    @NotBlank(message = "Nacionalidade e obrigatoria")
    @Column(length = 60, nullable = false)
    private String nacionalidade;

    public Clube(String nome, String nacionalidade) {
        this.nome = nome;
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return nome;
    }
}
