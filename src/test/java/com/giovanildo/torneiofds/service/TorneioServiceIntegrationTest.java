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
class TorneioServiceIntegrationTest {

    @Autowired TorneioService torneioService;
    @Autowired EAtletaRepository eAtletaRepository;
    @Autowired ClubeRepository clubeRepository;
    @Autowired TorneioRepository torneioRepository;

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
    void deveCriarTorneioComNomeUnico() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Teste", "Motivo"));
        assertNotNull(torneio.getId());
        assertEquals("Copa Teste", torneio.getNome());
    }

    @Test
    void deveRejeitarTorneioComNomeDuplicado() {
        torneioService.salvar(new Torneio("Copa Teste", "Motivo"));
        assertThrows(IllegalArgumentException.class,
                () -> torneioService.salvar(new Torneio("Copa Teste", "Outro motivo")));
    }

    @Test
    void deveAdicionarERemoverCompetidores() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Comp", null));

        Competidor c1 = torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());

        assertEquals(2, torneioService.listarCompetidores(torneio.getId()).size());

        torneioService.removerCompetidor(c1.getId());
        assertEquals(1, torneioService.listarCompetidores(torneio.getId()).size());
    }

    @Test
    void deveGerarPartidasRoundRobin() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa RR", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador3.getId(), clube3.getId());

        List<Partida> partidas = torneioService.gerarPartidas(torneio.getId());

        // 3 competidores: turno = 3 partidas, returno = 3 partidas = 6 total
        assertEquals(6, partidas.size());
        assertTrue(partidas.stream().noneMatch(Partida::isEncerrada));
    }

    @Test
    void deveRejeitarGeracaoDuplicadaDePartidas() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Dup", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());

        torneioService.gerarPartidas(torneio.getId());

        assertThrows(IllegalArgumentException.class,
                () -> torneioService.gerarPartidas(torneio.getId()));
    }

    @Test
    void deveRegistrarResultadoECalcularClassificacao() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Class", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());

        List<Partida> partidas = torneioService.gerarPartidas(torneio.getId());
        // 2 competidores: 1 turno + 1 returno = 2 partidas
        assertEquals(2, partidas.size());

        // Jogador1 ganha a primeira
        torneioService.registrarResultado(partidas.get(0).getId(), 3, 1);

        List<Classificacao> classificacao = torneioService.calcularClassificacao(torneio.getId());
        assertEquals(2, classificacao.size());

        // Primeiro da classificacao deve ter 3 pontos
        assertEquals(3, classificacao.get(0).getPontos());
        assertEquals(1, classificacao.get(0).getVitorias());
    }

    @Test
    void deveRejeitarGerarPartidasComMenosDeDoisCompetidores() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Solo", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());

        assertThrows(IllegalArgumentException.class,
                () -> torneioService.gerarPartidas(torneio.getId()));
    }

    @Test
    void deveDeletarTorneioEmCascata() {
        Torneio torneio = torneioService.salvar(new Torneio("Copa Del", null));
        torneioService.adicionarCompetidor(torneio.getId(), jogador1.getId(), clube1.getId());
        torneioService.adicionarCompetidor(torneio.getId(), jogador2.getId(), clube2.getId());
        torneioService.gerarPartidas(torneio.getId());

        Long id = torneio.getId();
        torneioService.deletar(id);

        assertFalse(torneioRepository.findById(id).isPresent());
    }

    private EAtleta criarJogador(String nome, String login, String senha) {
        return new EAtleta(nome, login, senha);
    }
}
