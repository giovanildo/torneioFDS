package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    @Query("""
            SELECT DISTINCT p FROM Partida p
            LEFT JOIN FETCH p.competidoresEmCampo cec
            LEFT JOIN FETCH cec.competidor c
            LEFT JOIN FETCH c.eAtleta
            LEFT JOIN FETCH c.clube
            WHERE p.torneio.id = :torneioId
            ORDER BY p.rodada
            """)
    List<Partida> findByTorneioIdOrderByRodada(Long torneioId);

    @Query("""
            SELECT p FROM Partida p
            LEFT JOIN FETCH p.competidoresEmCampo cec
            LEFT JOIN FETCH cec.competidor c
            LEFT JOIN FETCH c.eAtleta
            LEFT JOIN FETCH c.clube
            WHERE p.id = :id
            """)
    Optional<Partida> findByIdWithCompetidores(Long id);

    @Query("""
            SELECT DISTINCT p FROM Partida p
            LEFT JOIN FETCH p.competidoresEmCampo cec
            LEFT JOIN FETCH cec.competidor c
            LEFT JOIN FETCH c.eAtleta
            LEFT JOIN FETCH c.clube
            WHERE p.encerrada = true
            AND EXISTS (SELECT 1 FROM CompetidorEmCampo c1 WHERE c1.partida = p AND c1.competidor.eAtleta.id = :eAtletaId1)
            AND EXISTS (SELECT 1 FROM CompetidorEmCampo c2 WHERE c2.partida = p AND c2.competidor.eAtleta.id = :eAtletaId2)
            ORDER BY p.id
            """)
    List<Partida> findConfrontosDiretos(Long eAtletaId1, Long eAtletaId2);

    @Modifying
    @Query("DELETE FROM Partida p WHERE p.torneio.id = :torneioId")
    void deleteByTorneioId(Long torneioId);
}
