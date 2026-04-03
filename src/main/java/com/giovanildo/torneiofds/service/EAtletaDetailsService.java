package com.giovanildo.torneiofds.service;

import com.giovanildo.torneiofds.model.EAtleta;
import com.giovanildo.torneiofds.repository.EAtletaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EAtletaDetailsService implements UserDetailsService {

    private final EAtletaRepository eAtletaRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        EAtleta eAtleta = eAtletaRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("EAtleta nao encontrado: " + login));

        return new User(
                eAtleta.getLogin(),
                eAtleta.getSenha(),
                List.of(new SimpleGrantedAuthority("ROLE_" + eAtleta.getRole()))
        );
    }
}
