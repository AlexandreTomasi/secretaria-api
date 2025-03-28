package com.secretaria_api.repository;

import com.secretaria_api.model.FotoPessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FotoPessoaRepository extends CrudRepository<FotoPessoa, Long> {
}
