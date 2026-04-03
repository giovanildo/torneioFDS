package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {

    List<Partida> findByTorneioIdOrderByRodada(Long torneioId);
}
