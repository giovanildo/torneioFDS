package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Competidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetidorRepository extends JpaRepository<Competidor, Long> {

    List<Competidor> findByTorneioId(Long torneioId);
}
