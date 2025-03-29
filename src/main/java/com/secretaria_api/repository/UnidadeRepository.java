package com.secretaria_api.repository;

import com.secretaria_api.model.Unidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends CrudRepository<Unidade, Long> {
    Page<Unidade> findAll(Pageable pageable);
}

