package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.EAtleta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EAtletaRepository extends JpaRepository<EAtleta, Long> {

    Optional<EAtleta> findByLogin(String login);

    boolean existsByLogin(String login);
}
