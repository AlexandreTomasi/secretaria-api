package com.secretaria_api.service;

import com.secretaria_api.dto.LotacaoDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.Lotacao;
import com.secretaria_api.model.Pessoa;
import com.secretaria_api.model.Unidade;
import com.secretaria_api.repository.LotacaoRepository;
import com.secretaria_api.repository.PessoaRepository;
import com.secretaria_api.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LotacaoService {

    @Autowired
    LotacaoRepository lotacaoRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    UnidadeRepository unidadeRepository;


    public Page<Lotacao> listarTodos(Pageable pageable) {
        return lotacaoRepository.findAll(pageable);
    }


    public Lotacao salvarLotacao(LotacaoDTO lotacao) {
        if(lotacao == null){
            throw new RuntimeException("Obrigatorio enviar a lotação");
        }
        Pessoa pessoa = pessoaRepository.findById(lotacao.getPessoaId()).orElse(null);
        if(pessoa == null){
            throw new NotFoundException("Pessoa nao encontrada");
        }
        Unidade unidade = unidadeRepository.findById(lotacao.getUnidadeId()).orElse(null);
        if(unidade == null){
            throw new NotFoundException("Unidade nao encontrada");
        }
        Lotacao novo = new Lotacao();
        novo.setPessoa(pessoa);
        novo.setUnidade(unidade);
        novo.setDataLotacao(lotacao.getDataLotacao());
        novo.setDataRemocao(lotacao.getDataRemocao());
        novo.setPortaria(lotacao.getPortaria());
        novo = lotacaoRepository.save(novo);
        return novo;
    }

    public Lotacao atualizarLotacao(LotacaoDTO lotacao) {
        Lotacao novo = lotacaoRepository.findById(lotacao.getId()).orElse(null);
        if(novo == null){
            throw new NotFoundException("Lotacao nao encontrada");
        }

        Pessoa pessoa = pessoaRepository.findById(lotacao.getPessoaId()).orElse(null);
        if(pessoa == null){
            throw new NotFoundException("Pessoa nao encontrada");
        }
        Unidade unidade = unidadeRepository.findById(lotacao.getUnidadeId()).orElse(null);
        if(unidade == null){
            throw new NotFoundException("Unidade nao encontrada");
        }
        novo.setPessoa(pessoa);
        novo.setUnidade(unidade);
        novo.setDataLotacao(lotacao.getDataLotacao());
        novo.setDataRemocao(lotacao.getDataRemocao());
        novo.setPortaria(lotacao.getPortaria());
        novo = lotacaoRepository.save(novo);
        return novo;
    }

    public void deleteLotacao(Long id) {
        Lotacao Lotacao = lotacaoRepository.findById(id).orElse(null);
        if(Lotacao == null){
            throw new NotFoundException("Lotacao nao encontrada");
        }
        lotacaoRepository.deleteById(id);
    }

    public Lotacao buscaPorId(Long id) {
        Lotacao Lotacao = lotacaoRepository.findById(id).orElse(null);
        if(Lotacao == null){
            throw new NotFoundException("Lotacao nao encontrada");
        }
        return Lotacao;
    }
}
