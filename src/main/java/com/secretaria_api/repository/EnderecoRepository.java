package com.secretaria_api.repository;

import com.secretaria_api.model.Endereco;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

@Repository
public interface EnderecoRepository extends PagingAndSortingRepository<Endereco, Long> {
    Page<Endereco> findAll(Pageable pageable);
}