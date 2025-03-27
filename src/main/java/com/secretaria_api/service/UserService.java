package com.secretaria_api.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Simulação de um usuário fixo (você pode substituir por busca no banco de dados)
        if ("admin".equals(username)) {
            return new User("admin", "{noop}admin123", Collections.emptyList()); // Senha sem encoding
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }
}
