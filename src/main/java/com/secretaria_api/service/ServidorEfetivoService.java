package com.secretaria_api.service;

import com.secretaria_api.dto.EnderecoDTO;
import com.secretaria_api.dto.EnderecoFuncionalDTO;
import com.secretaria_api.dto.ServidorEfetivoDTO;
import com.secretaria_api.dto.ServidorUnidadeDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.*;
import com.secretaria_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServidorEfetivoService {
    @Autowired
    private ServidorEfetivoRepository servidorEfetivoRepository;

    @Autowired
    private FotoPessoaRepository fotoPessoaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private FotoPessoaService fotoPessoaService;

    @Autowired
    LotacaoRepository lotacaoRepository;

    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EnderecoRepository enderecoRepository;


    public List<EnderecoFuncionalDTO> consultarEnderecoFuncional(String parteNome) {
        //buscar servidor efetivo.
        List<ServidorEfetivo> servidores = servidorEfetivoRepository.findByPessoa_NomeContainingIgnoreCase(parteNome);
        if(servidores == null || servidores.isEmpty()){
            throw new NotFoundException("Nenhum servidor efetivo encontrado com esse nome");
        }
        //buscar unidade lotacao dele
        boolean temLotacaoAlgum = false;
        for(ServidorEfetivo servidor : servidores) {
            List<Lotacao> lotacoes = lotacaoRepository.findByPessoaId(servidor.getPesId());
            if(lotacoes != null && !lotacoes.isEmpty()){
                temLotacaoAlgum = true;
                break;
            }
        }
        if(temLotacaoAlgum == false){
            throw new NotFoundException("Servidor(es) com esse nome não estão lotados em nenhuma unidade");
        }

        List<Object[]> results =  servidorEfetivoRepository.findEnderecoFuncionalByParteNome(parteNome);
        List<EnderecoFuncionalDTO> resposta = results.stream()
                .map(EnderecoFuncionalDTO::fromObjectArray).toList();
        if (resposta.isEmpty()) {
            throw new NotFoundException("Nenhum endereço encontrado na unidade do servidor: " + parteNome);
        }
        return resposta;
    }


    public List<ServidorUnidadeDTO> consultarServidorEfetivoPorUni(Long unidade) {
        List<Object[]> servidores  =  servidorEfetivoRepository.findServidoresByUnidadeId(unidade);
        if(servidores  == null || servidores .isEmpty()){
            throw new NotFoundException("Nenhum servidor efetivo encontrado na unidade informada");
        }
        List<ServidorUnidadeDTO> resultado = new ArrayList<>();
        for (Object[] row : servidores) {
            Long pesId = ((Number) row[3]).longValue();
            List<FotoPessoa> fotos = fotoPessoaRepository.findByPessoaIdOrderByDataDesc(pesId);
            resultado.add(new ServidorUnidadeDTO(
                    (String) row[0], // nome
                    ((Number) row[1]).intValue(), // idade
                    (String) row[2], // unidade
                    fotoPessoaService.gerarListaLinkVariasFotosPessoa(fotos)
            ));
        }
        return resultado;
    }







    // Método para buscar todos os servidores efetivos com paginação
    public Page<ServidorEfetivo> getAllServidoresEfetivos(Pageable pageable) {
        Page<ServidorEfetivo> page = servidorEfetivoRepository.findAll(pageable);

        return page.map(servidor -> {
            if (servidor != null && servidor.getPessoa() != null && servidor.getPessoa().getFotos() != null) {
                servidor.getPessoa().setFotos(
                        fotoPessoaService.gerarLinkVariasFotosPessoa2(servidor.getPessoa().getFotos())
                );
            }
            return servidor;
        });
    }

    // Método para buscar um servidor efetivo por ID
    public ServidorEfetivo getServidorEfetivoById(Long id) {
        ServidorEfetivo resp =  servidorEfetivoRepository.findById(id).orElse(null);
        if(resp != null && resp.getPessoa() != null && resp.getPessoa().getFotos() != null){
            resp.getPessoa().setFotos(fotoPessoaService.gerarLinkVariasFotosPessoa2(resp.getPessoa().getFotos()));
        }
        return resp;
    }

    // Método para deletar um servidor efetivo por ID
    public void deleteServidorEfetivo(Long id) {
        ServidorEfetivo banco = servidorEfetivoRepository.findById(id).orElse(null);
        if(banco == null){
            throw new NotFoundException("Servidor efetivo nao encontrada");
        }
        servidorEfetivoRepository.deleteById(id);
    }

    public ServidorEfetivo salvarServidorTemporario(ServidorEfetivoDTO servidorEfetivoDTO) {
        if(servidorEfetivoDTO == null){
            throw new RuntimeException("Obrigatorio enviar o servidor");
        }
        Pessoa pessoa = new Pessoa(
                null,
                servidorEfetivoDTO.getNome(),
                servidorEfetivoDTO.getDataNascimento(),
                servidorEfetivoDTO.getSexo(),
                servidorEfetivoDTO.getMae(),
                servidorEfetivoDTO.getPai(),
                new ArrayList<>()
        );
        if(servidorEfetivoDTO.getEnderecoDTO() != null) {
            for (EnderecoDTO end :servidorEfetivoDTO.getEnderecoDTO()) {
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
        ServidorEfetivo novo = new ServidorEfetivo();
        novo.setPessoa(pessoa);
        novo.setMatricula(servidorEfetivoDTO.getMatricula());
        novo = servidorEfetivoRepository.save(novo);
        return novo;
    }

    public ServidorEfetivo atualizarServidorTemporario(ServidorEfetivoDTO servidorEfetivoDTO) {
        if(servidorEfetivoDTO == null || servidorEfetivoDTO.getPesId() == null){
            throw new RuntimeException("Obrigatorio enviar o servidor");
        }
        ServidorEfetivo servidorB = servidorEfetivoRepository.findById(servidorEfetivoDTO.getPesId()).orElse(null);
        if(servidorB == null){
            throw new NotFoundException("Servidor efetivo nao encontrado");
        }

        servidorB.getPessoa().setNome(servidorEfetivoDTO.getNome());
        servidorB.getPessoa().setDataNascimento(servidorEfetivoDTO.getDataNascimento());
        servidorB.getPessoa().setSexo(servidorEfetivoDTO.getSexo());
        servidorB.getPessoa().setMae(servidorEfetivoDTO.getMae());
        servidorB.getPessoa().setPai(servidorEfetivoDTO.getPai());
        servidorB.setMatricula(servidorEfetivoDTO.getMatricula());

        servidorB.getPessoa().getEnderecos().removeAll(servidorB.getPessoa().getEnderecos());
        if (servidorEfetivoDTO.getEnderecoDTO() != null) {
            for (EnderecoDTO end : servidorEfetivoDTO.getEnderecoDTO()) {
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

        servidorB = servidorEfetivoRepository.save(servidorB);
        return servidorB;
    }
}
