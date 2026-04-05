package com.giovanildo.torneiofds.controller;

import com.giovanildo.torneiofds.dto.*;
import com.giovanildo.torneiofds.model.Competidor;
import com.giovanildo.torneiofds.model.Partida;
import com.giovanildo.torneiofds.model.Torneio;
import com.giovanildo.torneiofds.service.TorneioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/torneios")
@RequiredArgsConstructor
@Tag(name = "Torneios", description = "Gerenciamento de torneios")
public class TorneioController {

    private final TorneioService torneioService;

    @GetMapping
    @Operation(summary = "Listar todos os torneios (paginado)")
    public Page<TorneioResponse> listar(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return torneioService.listarPaginado(pageable).map(TorneioResponse::from);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar torneio por ID")
    public TorneioResponse buscar(@PathVariable Long id) {
        return TorneioResponse.from(torneioService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo torneio")
    @ResponseStatus(HttpStatus.CREATED)
    public TorneioResponse criar(@Valid @RequestBody TorneioRequest request) {
        Torneio torneio = new Torneio(request.nome(), request.porqueDoNome());
        return TorneioResponse.from(torneioService.salvar(torneio));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar torneio")
    public TorneioResponse editar(@PathVariable Long id, @Valid @RequestBody TorneioRequest request) {
        return TorneioResponse.from(torneioService.editar(id, request.nome(), request.porqueDoNome()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar torneio")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        torneioService.deletar(id);
    }

    // --- Competidores ---

    @GetMapping("/{id}/competidores")
    @Operation(summary = "Listar competidores do torneio")
    public List<CompetidorResponse> listarCompetidores(@PathVariable Long id) {
        return torneioService.listarCompetidores(id).stream()
                .map(CompetidorResponse::from)
                .toList();
    }

    @PostMapping("/{id}/competidores")
    @Operation(summary = "Adicionar competidor ao torneio")
    @ResponseStatus(HttpStatus.CREATED)
    public CompetidorResponse adicionarCompetidor(@PathVariable Long id,
                                                  @Valid @RequestBody CompetidorRequest request) {
        Competidor c = torneioService.adicionarCompetidor(id, request.eAtletaId(), request.clubeId());
        return CompetidorResponse.from(c);
    }

    @DeleteMapping("/{id}/competidores/{competidorId}")
    @Operation(summary = "Remover competidor do torneio")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removerCompetidor(@PathVariable Long id, @PathVariable Long competidorId) {
        torneioService.removerCompetidor(competidorId);
    }

    // --- Partidas ---

    @PostMapping("/{id}/partidas/gerar")
    @Operation(summary = "Gerar partidas round-robin (turno e returno)")
    @ResponseStatus(HttpStatus.CREATED)
    public List<PartidaResponse> gerarPartidas(@PathVariable Long id) {
        return torneioService.gerarPartidas(id).stream()
                .map(PartidaResponse::from)
                .toList();
    }

    @GetMapping("/{id}/partidas")
    @Operation(summary = "Listar partidas do torneio")
    public List<PartidaResponse> listarPartidas(@PathVariable Long id) {
        return torneioService.listarPartidas(id).stream()
                .map(PartidaResponse::from)
                .toList();
    }

    @PutMapping("/{id}/partidas/{partidaId}/resultado")
    @Operation(summary = "Registrar resultado de uma partida")
    public PartidaResponse registrarResultado(@PathVariable Long id,
                                              @PathVariable Long partidaId,
                                              @Valid @RequestBody ResultadoRequest request) {
        torneioService.registrarResultado(partidaId, request.golsAnfitriao(), request.golsVisitante());
        Partida partida = torneioService.buscarPartida(partidaId);
        return PartidaResponse.from(partida);
    }

    // --- Classificacao ---

    @GetMapping("/{id}/classificacao")
    @Operation(summary = "Tabela de classificacao do torneio")
    public List<com.giovanildo.torneiofds.model.Classificacao> classificacao(@PathVariable Long id) {
        return torneioService.calcularClassificacao(id);
    }

    // --- Confronto direto ---

    @GetMapping("/confronto")
    @Operation(summary = "Historico de confrontos diretos entre dois jogadores")
    public ConfrontoResponse confrontoDireto(@RequestParam Long jogador1, @RequestParam Long jogador2) {
        return torneioService.confrontoDireto(jogador1, jogador2);
    }
}
