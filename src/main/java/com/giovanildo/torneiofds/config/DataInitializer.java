package com.giovanildo.torneiofds.config;

import com.giovanildo.torneiofds.model.*;
import com.giovanildo.torneiofds.repository.ClubeRepository;
import com.giovanildo.torneiofds.repository.EAtletaRepository;
import com.giovanildo.torneiofds.repository.TorneioRepository;
import com.giovanildo.torneiofds.service.PremioService;
import com.giovanildo.torneiofds.service.TorneioService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final EAtletaRepository eAtletaRepository;
    private final ClubeRepository clubeRepository;
    private final TorneioRepository torneioRepository;
    private final TorneioService torneioService;
    private final PremioService premioService;
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

        // Seed: cria torneios ficticios se nao existir nenhum
        if (torneioRepository.count() == 0) {
            seedTorneios();
        }
    }

    private void seedTorneios() {
        // Cria jogadores
        EAtleta giova = criarJogadorSeNaoExiste("Giovanildo", "giova");
        EAtleta tiago = criarJogadorSeNaoExiste("Tiago", "tiago");
        EAtleta rafael = criarJogadorSeNaoExiste("Rafael", "rafael");
        EAtleta bruno = criarJogadorSeNaoExiste("Bruno", "bruno");

        List<Clube> clubes = clubeRepository.findAll();

        String[][] torneios = {
                {"Copa FDS 2024",        "Primeiro torneio",       "0,1,2,3"},
                {"Liga Inverno 2024",    "Torneio de inverno",     "4,5,6,7"},
                {"Copa Primavera 2024",  "Torneio da primavera",   "0,5,2,7"},
                {"Super Copa 2024",      "Edicao especial",        "1,4,3,6"},
                {"Copa Verao 2025",      "Torneio de verao",       "5,0,7,2"},
                {"Liga Outono 2025",     "Torneio de outono",      "3,6,1,4"},
                {"Copa FDS 2025",        "Segunda edicao",         "0,1,2,3"},
                {"Liga Inverno 2025",    "Segundo inverno",        "4,5,6,7"},
                {"Copa Primavera 2025",  "Segunda primavera",      "2,0,4,1"},
                {"Super Copa 2025",      "Edicao especial II",     "5,3,7,6"},
                {"Copa Verao 2026",      "Terceiro verao",         "0,4,2,5"},
                {"Liga Outono 2026",     "Segundo outono",         "1,6,3,7"},
                {"Copa FDS 2026",        "Terceira edicao",        "0,5,2,7"},
        };

        EAtleta[] jogadores = {giova, tiago, rafael, bruno};

        // Seed diferente para cada torneio, mas Bruno tende a perder
        // para garantir coca-colas e eventualmente o ibis
        for (int t = 0; t < torneios.length; t++) {
            String nome = torneios[t][0];
            String porque = torneios[t][1];
            String[] clubeIdx = torneios[t][2].split(",");

            Torneio torneio = torneioService.salvar(new Torneio(nome, porque));
            Long tid = torneio.getId();

            for (int j = 0; j < 4; j++) {
                torneioService.adicionarCompetidor(tid, jogadores[j].getId(),
                        clubes.get(Integer.parseInt(clubeIdx[j])).getId());
            }

            List<Partida> partidas = torneioService.gerarPartidas(tid);
            Random rng = new Random(t * 7 + 13);

            for (Partida p : partidas) {
                CompetidorEmCampo anf = p.getAnfitriao();
                CompetidorEmCampo vis = p.getVisitante();

                int golsAnf = rng.nextInt(4);
                int golsVis = rng.nextInt(4);

                // Bruno perde mais: se ele joga, adversario ganha bonus
                boolean brunoAnf = anf.getCompetidor().getEAtleta().getId().equals(bruno.getId());
                boolean brunoVis = vis.getCompetidor().getEAtleta().getId().equals(bruno.getId());

                if (brunoAnf) {
                    golsVis += 2;
                    golsAnf = Math.max(0, golsAnf - 1);
                } else if (brunoVis) {
                    golsAnf += 2;
                    golsVis = Math.max(0, golsVis - 1);
                }

                // Giova tende a ganhar mais
                boolean giovaAnf = anf.getCompetidor().getEAtleta().getId().equals(giova.getId());
                boolean giovaVis = vis.getCompetidor().getEAtleta().getId().equals(giova.getId());

                if (giovaAnf && !brunoVis) {
                    golsAnf += 1;
                } else if (giovaVis && !brunoAnf) {
                    golsVis += 1;
                }

                torneioService.registrarResultado(p.getId(), golsAnf, golsVis);
            }

            premioService.gerarPremios(tid);
        }
    }

    private EAtleta criarJogadorSeNaoExiste(String nome, String login) {
        return eAtletaRepository.findByLogin(login)
                .orElseGet(() -> eAtletaRepository.save(
                        new EAtleta(nome, login, passwordEncoder.encode("123456"))));
    }

}
