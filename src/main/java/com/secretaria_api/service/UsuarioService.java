package com.secretaria_api.service;

import com.secretaria_api.model.Usuario;
import com.secretaria_api.repository.UsuarioRepository;
import com.secretaria_api.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JwtService jwtService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Simulação de um usuário fixo (você pode substituir por busca no banco de dados)
        if ("admin".equals(username)) {
            return new User("admin", "{noop}admin123", Collections.emptyList()); // Senha sem encoding
        }
        throw new UsernameNotFoundException("Usuário não encontrado");
    }

    public Usuario salvarUsuario(Usuario usuario) {
        PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Page<Usuario> listarTodos(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }
}
