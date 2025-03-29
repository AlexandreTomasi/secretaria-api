package com.secretaria_api.service;

import com.secretaria_api.model.Cidade;
import com.secretaria_api.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CidadeService {

    @Autowired
    CidadeRepository cidadeRepository;

    public Page<Cidade> listarTodos(Pageable pageable) {
        return cidadeRepository.findAll(pageable);
    }
}
