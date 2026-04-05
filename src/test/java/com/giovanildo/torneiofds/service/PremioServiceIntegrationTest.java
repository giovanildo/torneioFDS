package com.giovanildo.torneiofds.service;

import com.giovanildo.torneiofds.model.*;
import com.giovanildo.torneiofds.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PremioServiceIntegrationTest {

    @Autowired TorneioService torneioService;
    @Autowired PremioService premioService;
    @Autowired EAtletaRepository eAtletaRepository;
    @Autowired ClubeRepository clubeRepository;
    @Autowired PremioRepository premioRepository;

    private EAtleta jogador1, jogador2, jogador3;
    private Clube clube1, clube2, clube3;

    @BeforeEach
    void setUp() {
        jogador1 = eAtletaRepository.save(criarJogador("Giova", "giova", "123"));
        jogador2 = eAtletaRepository.save(criarJogador("Tiago", "tiago", "123"));
        jogador3 = eAtletaRepository.save(criarJogador("Rafael", "rafael", "123"));

        clube1 = clubeRepository.save(new Clube("Flamengo", "Brasil"));
        clube2 = clubeRepository.save(new Clube("Palmeiras", "Brasil"));
        clube3 = clubeRepository.save(new Clube("Santos", "Brasil"));
    }

    @Test
    void deveGerarPremiosAoEncerrarTodasPartidas() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Premio", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador3.getId(), clube3.getId());

        List<Partida> partidas = torneioService.gerarPartidas(torneio.getId());

        // Registra todos os resultados (jogador1 sempre ganha)
        for (Partida p : partidas) {
            torneioService.registrarResultado(p.getId(), 3, 0);
        }

        // Premios devem ter sido gerados automaticamente
        List<Premio> premios = premioRepository.findByTorneioId(torneio.getId());
        assertFalse(premios.isEmpty());

        // Verifica tipos esperados
        assertTrue(premios.stream().anyMatch(p -> p.getTipo() == TipoPremio.CAMPEAO));
        assertTrue(premios.stream().anyMatch(p -> p.getTipo() == TipoPremio.VICE_CAMPEAO));
        assertTrue(premios.stream().anyMatch(p -> p.getTipo() == TipoPremio.ARTILHEIRO));
        assertTrue(premios.stream().anyMatch(p -> p.getTipo() == TipoPremio.MENOS_VAZADA));
        assertTrue(premios.stream().anyMatch(p -> p.getTipo() == TipoPremio.COCA_COLA));
        assertTrue(premios.stream().anyMatch(p -> p.getTipo() == TipoPremio.ESCAPOU_DA_COCA_COLA));
    }

    @Test
    void deveRejeitarGeracaoDuplicadaDePremios() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Dup Premio", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());

        List<Partida> partidas = torneioService.gerarPartidas(torneio.getId());
        for (Partida p : partidas) {
            torneioService.registrarResultado(p.getId(), 2, 1);
        }

        // Premios ja foram gerados automaticamente
        assertThrows(IllegalArgumentException.class,
                () -> premioService.gerarPremios(torneio.getId()));
    }

    @Test
    void naoDeveGerarEscapouDaCocaColaComDoisCompetidores() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Dois", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());

        List<Partida> partidas = torneioService.gerarPartidas(torneio.getId());
        for (Partida p : partidas) {
            torneioService.registrarResultado(p.getId(), 1, 0);
        }

        List<Premio> premios = premioRepository.findByTorneioId(torneio.getId());
        assertFalse(premios.stream().anyMatch(p -> p.getTipo() == TipoPremio.ESCAPOU_DA_COCA_COLA));
    }

    @Test
    void deveContarCocaColasDoJogador() {
        // Cria torneio onde jogador2 fica em ultimo
        Torneio t1 = torneioService.salvar(new Torneio("Copa Coca 1", null));
        torneioService.adicionarCompetidor(t1.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(t1.getId(), jogador2.getId(), clube2.getId());

        List<Partida> partidas = torneioService.gerarPartidas(t1.getId());
        for (Partida p : partidas) {
            torneioService.registrarResultado(p.getId(), 5, 0);
        }

        long cocas = premioService.contarCocaColas(jogador2.getId());
        assertTrue(cocas >= 1);
    }

    private EAtleta criarJogador(String nome, String login, String senha) {
        return new EAtleta(nome, login, senha);
    }
}
