package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Competidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompetidorRepository extends JpaRepository<Competidor, Long> {

    List<Competidor> findByTorneioId(Long torneioId);

    @Modifying
    @Query("DELETE FROM Competidor c WHERE c.torneio.id = :torneioId")
    void deleteByTorneioId(Long torneioId);
}
