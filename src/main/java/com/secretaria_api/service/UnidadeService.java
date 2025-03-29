package com.secretaria_api.service;

import com.secretaria_api.dto.EnderecoDTO;
import com.secretaria_api.dto.UnidadeDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.Cidade;
import com.secretaria_api.model.Endereco;
import com.secretaria_api.model.Unidade;
import com.secretaria_api.repository.CidadeRepository;
import com.secretaria_api.repository.EnderecoRepository;
import com.secretaria_api.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UnidadeService {

    @Autowired
    UnidadeRepository unidadeRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public Page<Unidade> listarTodos(Pageable pageable) {
        return unidadeRepository.findAll(pageable);
    }


    public Unidade salvarUnidade(UnidadeDTO unidade) {
        Unidade unidade1 = new Unidade();
        unidade1.setNome(unidade.getNome());
        unidade1.setSigla(unidade.getSigla());
        unidade1.setEnderecos(new ArrayList<>());
        if(unidade.getEnderecos() != null) {
            for (EnderecoDTO end :unidade.getEnderecos()) {
                Cidade cidad = cidadeRepository.findById(end.getCidadeId()).orElseThrow();
                unidade1.getEnderecos().add(new Endereco(
                        null,
                        end.getTipoLogradouro(),
                        end.getLogradouro(),
                        end.getNumero(),
                        end.getBairro(),
                        cidad,
                        null
                ));
            }
        }
        unidade1 =  unidadeRepository.save(unidade1);
        return unidade1;
    }

    public Unidade alterarUnidade(UnidadeDTO unidade) {
        Unidade uniB = unidadeRepository.findById(unidade.getId()).orElse(null);
        if(uniB == null){
            throw new NotFoundException("Unidade não encontrada");
        }

        uniB.setNome(unidade.getNome());
        uniB.setSigla(unidade.getSigla());
        uniB.getEnderecos().removeAll(uniB.getEnderecos());
        if(unidade.getEnderecos() != null) {
            for (EnderecoDTO end :unidade.getEnderecos()) {
                Cidade cidad = cidadeRepository.findById(end.getCidadeId()).orElseThrow();
                uniB.getEnderecos().add(new Endereco(
                        end.getId(),
                        end.getTipoLogradouro(),
                        end.getLogradouro(),
                        end.getNumero(),
                        end.getBairro(),
                        cidad,
                        null
                ));
            }
        }
        uniB =  unidadeRepository.save(uniB);
        return uniB;
    }

    public void deleteUnidade(Long id) {
        Unidade unidade = unidadeRepository.findById(id).orElse(null);
        if(unidade == null){
            throw new NotFoundException("Unidade nao encontrada");
        }
        unidadeRepository.deleteById(id);
    }

    public Unidade buscaPorId(Long id) {
        Unidade unidade = unidadeRepository.findById(id).orElse(null);
        if(unidade == null){
            throw new NotFoundException("Unidade nao encontrada");
        }
        return unidade;
    }
}
