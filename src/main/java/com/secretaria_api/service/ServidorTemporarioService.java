package com.secretaria_api.service;

import com.secretaria_api.dto.ServidorTemporarioDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.Pessoa;
import com.secretaria_api.model.ServidorTemporario;
import com.secretaria_api.repository.PessoaRepository;
import com.secretaria_api.repository.ServidorTemporarioRepository;
import com.secretaria_api.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ServidorTemporarioService {

    @Autowired
    ServidorTemporarioRepository servidorTemporarioRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    UnidadeRepository unidadeRepository;


    public Page<ServidorTemporario> listarTodos(Pageable pageable) {
        return servidorTemporarioRepository.findAll(pageable);
    }

    public void deleteServidorTemporario(Long id) {
        ServidorTemporario ServidorTemporario = servidorTemporarioRepository.findById(id).orElse(null);
        if(ServidorTemporario == null){
            throw new NotFoundException("Servidor Temporario nao encontrado");
        }
        servidorTemporarioRepository.deleteById(id);
    }

    public ServidorTemporario buscaPorId(Long id) {
        ServidorTemporario ServidorTemporario = servidorTemporarioRepository.findById(id).orElse(null);
        if(ServidorTemporario == null){
            throw new NotFoundException("Servidor Temporario nao encontrado");
        }
        return ServidorTemporario;
    }

    public ServidorTemporario salvarServidorTemporario(ServidorTemporarioDTO servidorTemporario) {
        if(servidorTemporario == null){
            throw new RuntimeException("Obrigatorio enviar o servidor");
        }
        Pessoa pessoa = new Pessoa(
                null,
                servidorTemporario.getNome(),
                servidorTemporario.getDataNascimento(),
                servidorTemporario.getSexo(),
                servidorTemporario.getMae(),
                servidorTemporario.getPai()
                );
        ServidorTemporario novo = new ServidorTemporario();
        novo.setPessoa(pessoa);
        novo.setDataAdmissao(servidorTemporario.getDataAdmissao());
        novo.setDataDemissao(servidorTemporario.getDataDemissao());
        novo = servidorTemporarioRepository.save(novo);
        return novo;
    }

    public ServidorTemporario atualizarServidorTemporario(ServidorTemporarioDTO servidorTemporario) {
        if(servidorTemporario == null || servidorTemporario.getPesId() == null){
            throw new RuntimeException("Obrigatorio enviar o servidor");
        }
        ServidorTemporario servidorB = servidorTemporarioRepository.findById(servidorTemporario.getPesId()).orElse(null);
        if(servidorB == null){
            throw new NotFoundException("Servidor Temporario nao encontrado");
        }

        servidorB.getPessoa().setNome(servidorTemporario.getNome());
        servidorB.getPessoa().setDataNascimento(servidorTemporario.getDataNascimento());
        servidorB.getPessoa().setSexo(servidorTemporario.getSexo());
        servidorB.getPessoa().setMae(servidorTemporario.getMae());
        servidorB.getPessoa().setPai(servidorTemporario.getPai());
        servidorB.setDataDemissao(servidorTemporario.getDataDemissao());
        servidorB.setDataAdmissao(servidorTemporario.getDataAdmissao());

        servidorB = servidorTemporarioRepository.save(servidorB);
        return servidorB;
    }


}
