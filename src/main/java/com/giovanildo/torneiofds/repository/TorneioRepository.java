package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Torneio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TorneioRepository extends JpaRepository<Torneio, Long> {

    boolean existsByNome(String nome);

    @Query("SELECT t FROM Torneio t LEFT JOIN FETCH t.competidores ORDER BY t.id DESC")
    List<Torneio> findAllByOrderByIdDesc();

    @Query("SELECT t FROM Torneio t LEFT JOIN FETCH t.competidores WHERE t.id = :id")
    Optional<Torneio> findByIdWithCompetidores(Long id);
}
