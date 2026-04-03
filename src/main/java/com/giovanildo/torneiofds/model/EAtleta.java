package com.giovanildo.torneiofds.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "tab_eatleta")
@Getter @Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class EAtleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do jogador e obrigatorio")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "Login e obrigatorio")
    @Column(nullable = false, unique = true)
    private String login;

    @NotBlank(message = "Senha e obrigatoria")
    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String role = "USER";

    public EAtleta(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.role = "USER";
    }

    @Override
    public String toString() {
        return nome;
    }
}
