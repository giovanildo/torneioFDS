package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.CompetidorEmCampo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompetidorEmCampoRepository extends JpaRepository<CompetidorEmCampo, Long> {

    @Modifying
    @Query("DELETE FROM CompetidorEmCampo c WHERE c.partida.torneio.id = :torneioId")
    void deleteByTorneioId(Long torneioId);
}
