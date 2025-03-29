package com.secretaria_api.repository;

import com.secretaria_api.model.Lotacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotacaoRepository extends CrudRepository<Lotacao, Long> {
    Page<Lotacao> findAll(Pageable pageable);

    List<Lotacao> findByPessoaId(Long idPessoa);
}
