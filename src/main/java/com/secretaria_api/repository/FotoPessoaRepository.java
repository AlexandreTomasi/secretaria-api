package com.secretaria_api.repository;

import com.secretaria_api.model.FotoPessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FotoPessoaRepository extends CrudRepository<FotoPessoa, Long> {

    List<FotoPessoa> findByPessoaIdOrderByDataDesc(Long pesId);
}
