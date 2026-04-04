package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByTorneioIdOrderByRodada(Long torneioId);

    @Modifying
    @Query("DELETE FROM Partida p WHERE p.torneio.id = :torneioId")
    void deleteByTorneioId(Long torneioId);
}
