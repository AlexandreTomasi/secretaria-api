package com.secretaria_api.service;

import com.secretaria_api.dto.EnderecoDTO;
import com.secretaria_api.dto.PessoaDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.Cidade;
import com.secretaria_api.model.Endereco;
import com.secretaria_api.model.Pessoa;
import com.secretaria_api.repository.CidadeRepository;
import com.secretaria_api.repository.EnderecoRepository;
import com.secretaria_api.repository.PessoaRepository;
import com.secretaria_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class PessoaService {
    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    public Page<Pessoa> listarTodos(Pageable pageable) {
        return pessoaRepository.findAll(pageable);
    }

    public void deletePessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
        if(pessoa == null){
            throw new NotFoundException("Pessoa nao encontrada");
        }
        pessoaRepository.deleteById(id);
    }

    public Pessoa buscaPorId(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).orElse(null);
        if(pessoa == null){
            throw new NotFoundException("Pessoa nao encontrada");
        }
        return pessoa;
    }


    public Pessoa salvarPessoa(PessoaDTO pessoa) {
        Pessoa pessoaNova = new Pessoa();
        pessoaNova.setNome(pessoa.getNome());
        pessoaNova.setDataNascimento(pessoa.getDataNascimento());
        pessoaNova.setSexo(pessoa.getSexo());
        pessoaNova.setMae(pessoa.getMae());
        pessoaNova.setPai(pessoa.getPai());
        pessoaNova.setEnderecos(new ArrayList<>());
        if(pessoa.getEnderecos() != null) {
            for (EnderecoDTO end :pessoa.getEnderecos()) {
                Cidade cidad = cidadeRepository.findById(end.getCidadeId()).orElseThrow();
                pessoaNova.getEnderecos().add(new Endereco(
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
        pessoaNova =  pessoaRepository.save(pessoaNova);
        return pessoaNova;
    }

    public Pessoa alterarPessoa(PessoaDTO pessoaDTO) {
        Pessoa uniB = pessoaRepository.findById(pessoaDTO.getId()).orElse(null);
        if(uniB == null){
            throw new NotFoundException("Pessoa não encontrada");
        }

        uniB.setNome(pessoaDTO.getNome());
        uniB.setDataNascimento(pessoaDTO.getDataNascimento());
        uniB.setSexo(pessoaDTO.getSexo());
        uniB.setMae(pessoaDTO.getMae());
        uniB.setPai(pessoaDTO.getPai());
        uniB.getEnderecos().removeAll(uniB.getEnderecos());
        if(pessoaDTO.getEnderecos() != null) {
            for (EnderecoDTO end :pessoaDTO.getEnderecos()) {
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
        uniB =  pessoaRepository.save(uniB);
        return uniB;
    }


}
