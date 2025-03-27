package com.secretaria_api.repository;

import com.secretaria_api.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    Page<Usuario> findAll(Pageable pageable);

    Optional<Usuario> findByLogin(String login);
}
