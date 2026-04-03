package com.giovanildo.torneiofds.config;

import com.giovanildo.torneiofds.model.Clube;
import com.giovanildo.torneiofds.model.EAtleta;
import com.giovanildo.torneiofds.repository.ClubeRepository;
import com.giovanildo.torneiofds.repository.EAtletaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EAtletaRepository eAtletaRepository;
    private final ClubeRepository clubeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Cria usuario admin se nao existir
        if (!eAtletaRepository.existsByLogin("admin")) {
            EAtleta admin = new EAtleta("Administrador", "admin", passwordEncoder.encode("admin"));
            admin.setRole("ADMIN");
            eAtletaRepository.save(admin);
        }

        // Clubes iniciais se tabela vazia
        if (clubeRepository.count() == 0) {
            clubeRepository.save(new Clube("Flamengo", "Brasil"));
            clubeRepository.save(new Clube("Vasco", "Brasil"));
            clubeRepository.save(new Clube("Palmeiras", "Brasil"));
            clubeRepository.save(new Clube("Corinthians", "Brasil"));
            clubeRepository.save(new Clube("Barcelona", "Espanha"));
            clubeRepository.save(new Clube("Real Madrid", "Espanha"));
            clubeRepository.save(new Clube("Manchester City", "Inglaterra"));
            clubeRepository.save(new Clube("Liverpool", "Inglaterra"));
        }
    }
}
