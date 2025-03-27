package com.secretaria_api.repository;

import com.secretaria_api.model.Lotacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LotacaoRepository extends PagingAndSortingRepository<Lotacao, Long> {
    Page<Lotacao> findAll(Pageable pageable);
}
