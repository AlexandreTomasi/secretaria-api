package com.secretaria_api.service;

import com.secretaria_api.dto.EnderecoDTO;
import com.secretaria_api.dto.ServidorTemporarioDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.Cidade;
import com.secretaria_api.model.Endereco;
import com.secretaria_api.model.Pessoa;
import com.secretaria_api.model.ServidorTemporario;
import com.secretaria_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ServidorTemporarioService {

    @Autowired
    ServidorTemporarioRepository servidorTemporarioRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    UnidadeRepository unidadeRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EnderecoRepository enderecoRepository;

    @Autowired
    private FotoPessoaService fotoPessoaService;


    public Page<ServidorTemporario> listarTodos(Pageable pageable) {
        Page<ServidorTemporario> page = servidorTemporarioRepository.findAll(pageable);
        return page.map(servidor -> {
            if (servidor != null && servidor.getPessoa() != null && servidor.getPessoa().getFotos() != null) {
                servidor.getPessoa().setFotos(
                        fotoPessoaService.gerarLinkVariasFotosPessoa2(servidor.getPessoa().getFotos())
                );
            }
            return servidor;
        });
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
        if(ServidorTemporario != null && ServidorTemporario.getPessoa() != null && ServidorTemporario.getPessoa().getFotos() != null){
            ServidorTemporario.getPessoa().setFotos(fotoPessoaService.gerarLinkVariasFotosPessoa2(ServidorTemporario.getPessoa().getFotos()));
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
                servidorTemporario.getPai(),
                new ArrayList<>()
                );
        if(servidorTemporario.getEnderecoDTO() != null) {
            for (EnderecoDTO end :servidorTemporario.getEnderecoDTO()) {
                Cidade cidad = cidadeRepository.findById(end.getCidadeId()).orElseThrow();
                pessoa.getEnderecos().add(new Endereco(
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
        try {
            servidorB.getPessoa().setNome(servidorTemporario.getNome());
            servidorB.getPessoa().setDataNascimento(servidorTemporario.getDataNascimento());
            servidorB.getPessoa().setSexo(servidorTemporario.getSexo());
            servidorB.getPessoa().setMae(servidorTemporario.getMae());
            servidorB.getPessoa().setPai(servidorTemporario.getPai());
            servidorB.setDataDemissao(servidorTemporario.getDataDemissao());
            servidorB.setDataAdmissao(servidorTemporario.getDataAdmissao());

            servidorB.getPessoa().getEnderecos().removeAll(servidorB.getPessoa().getEnderecos());
            if (servidorTemporario.getEnderecoDTO() != null) {
                for (EnderecoDTO end : servidorTemporario.getEnderecoDTO()) {
                    Cidade cidad = cidadeRepository.findById(end.getCidadeId()).orElseThrow();
                    if(end.getId() != null){
                        Endereco endereco = enderecoRepository.findById(end.getId()).orElseThrow();
                        endereco.setTipoLogradouro(end.getTipoLogradouro());
                        endereco.setLogradouro(end.getLogradouro());
                        endereco.setNumero(end.getNumero());
                        endereco.setBairro(end.getBairro());
                        endereco.setCidade(cidad);
                        servidorB.getPessoa().getEnderecos().add(endereco);
                    }else {
                        servidorB.getPessoa().getEnderecos().add(new Endereco(
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
            }

            servidorB = servidorTemporarioRepository.save(servidorB);
            return servidorB;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }


}
