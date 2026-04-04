package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Premio;
import com.giovanildo.torneiofds.model.TipoPremio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PremioRepository extends JpaRepository<Premio, Long> {

    @Query("SELECT p FROM Premio p WHERE p.eAtleta.id = :eAtletaId")
    List<Premio> findByEAtletaId(Long eAtletaId);

    List<Premio> findByTorneioId(Long torneioId);

    boolean existsByTorneioId(Long torneioId);

    @Query("SELECT COUNT(p) FROM Premio p WHERE p.eAtleta.id = :eAtletaId AND p.tipo = :tipo")
    long countByEAtletaIdAndTipo(Long eAtletaId, TipoPremio tipo);

    @Modifying
    @Query("DELETE FROM Premio p WHERE p.torneio.id = :torneioId")
    void deleteByTorneioId(Long torneioId);
}
