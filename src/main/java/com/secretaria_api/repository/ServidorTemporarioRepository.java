package com.secretaria_api.repository;

import com.secretaria_api.model.ServidorTemporario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServidorTemporarioRepository extends PagingAndSortingRepository<ServidorTemporario, Long> {
    Page<ServidorTemporario> findAll(Pageable pageable);
}