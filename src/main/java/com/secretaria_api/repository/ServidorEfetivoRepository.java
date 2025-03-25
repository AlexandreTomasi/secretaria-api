package com.secretaria_api.repository;

import com.secretaria_api.model.Pessoa;
import com.secretaria_api.model.ServidorEfetivo;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface ServidorEfetivoRepository extends PagingAndSortingRepository<ServidorEfetivo, Long> {
    Page<ServidorEfetivo> findAll(Pageable pageable);
}