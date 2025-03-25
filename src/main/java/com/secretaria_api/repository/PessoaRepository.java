package com.secretaria_api.repository;

import com.secretaria_api.model.Pessoa;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface PessoaRepository extends PagingAndSortingRepository<Pessoa, Long> {
    Page<Pessoa> findAll(Pageable pageable);
}
