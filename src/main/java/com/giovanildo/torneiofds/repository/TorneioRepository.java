package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TorneioRepository extends JpaRepository<Torneio, Long> {

    boolean existsByNome(String nome);
}
