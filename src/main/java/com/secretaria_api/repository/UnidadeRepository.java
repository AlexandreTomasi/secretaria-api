package com.secretaria_api.repository;

import com.secretaria_api.model.Unidade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeRepository extends PagingAndSortingRepository<Unidade, Long> {
    Page<Unidade> findAll(Pageable pageable);
}

