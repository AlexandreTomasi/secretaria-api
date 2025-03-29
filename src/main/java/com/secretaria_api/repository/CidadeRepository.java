package com.secretaria_api.repository;

import com.secretaria_api.model.Cidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CidadeRepository  extends CrudRepository<Cidade, Long> {
    Page<Cidade> findAll(Pageable pageable);
}
