package com.giovanildo.torneiofds.repository;

import com.giovanildo.torneiofds.model.Clube;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubeRepository extends JpaRepository<Clube, Long> {
}
