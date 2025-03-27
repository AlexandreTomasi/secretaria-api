package com.secretaria_api.repository;

import com.secretaria_api.model.ServidorEfetivo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServidorEfetivoRepository extends CrudRepository<ServidorEfetivo, Long> {
    Page<ServidorEfetivo> findAll(Pageable pageable);
}