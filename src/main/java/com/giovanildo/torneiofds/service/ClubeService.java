package com.giovanildo.torneiofds.service;

import com.giovanildo.torneiofds.model.Clube;
import com.giovanildo.torneiofds.repository.ClubeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubeService {

    private final ClubeRepository clubeRepository;

    public List<Clube> listarTodos() {
        return clubeRepository.findAll();
    }

    @Transactional
    public Clube salvar(Clube clube) {
        return clubeRepository.save(clube);
    }
}
