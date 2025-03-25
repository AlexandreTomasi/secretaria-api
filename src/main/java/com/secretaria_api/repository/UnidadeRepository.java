package com.secretaria_api.repository;

import com.secretaria_api.model.Unidade;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface UnidadeRepository extends PagingAndSortingRepository<Unidade, Long> {
    Page<Unidade> findAll(Pageable pageable);
}

