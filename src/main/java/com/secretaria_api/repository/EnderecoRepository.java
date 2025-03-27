package com.secretaria_api.repository;

import com.secretaria_api.model.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends PagingAndSortingRepository<Endereco, Long> {
    Page<Endereco> findAll(Pageable pageable);
}