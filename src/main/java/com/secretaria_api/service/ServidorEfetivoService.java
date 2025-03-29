package com.secretaria_api.service;

import com.secretaria_api.dto.EnderecoFuncionalDTO;
import com.secretaria_api.dto.ServidorEfetivoDTO;
import com.secretaria_api.dto.ServidorUnidadeDTO;
import com.secretaria_api.exception.NotFoundException;
import com.secretaria_api.model.FotoPessoa;
import com.secretaria_api.model.Pessoa;
import com.secretaria_api.model.ServidorEfetivo;
import com.secretaria_api.repository.FotoPessoaRepository;
import com.secretaria_api.repository.PessoaRepository;
import com.secretaria_api.repository.ServidorEfetivoRepository;
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

    public List<EnderecoFuncionalDTO> consultarEnderecoFuncional(String parteNome) {
        List<Object[]> results =  servidorEfetivoRepository.findEnderecoFuncionalByParteNome(parteNome);
        return results.stream()
                .map(EnderecoFuncionalDTO::fromObjectArray)
                .collect(Collectors.toList());
    }


    public List<ServidorUnidadeDTO> consultarServidorEfetivoPorUni(Long unidade) {
        List<Object[]> servidores  =  servidorEfetivoRepository.findServidoresByUnidadeId(unidade);
        if(servidores  == null || servidores .isEmpty()){
            throw new NotFoundException("Nenhum servidor encontrado na unidade informada");
        }
        List<ServidorUnidadeDTO> resultado = new ArrayList<>();
        for (Object[] row : servidores) {
            Long pesId = ((Number) row[3]).longValue();
            List<FotoPessoa> fotos = fotoPessoaRepository.findByPessoaIdOrderByDataDesc(pesId);
            resultado.add(new ServidorUnidadeDTO(
                    (String) row[0], // nome
                    ((Number) row[1]).intValue(), // idade
                    (String) row[2], // unidade
                    fotoPessoaService.gerarLinkVariasFotosPessoa(fotos)
            ));
        }
        return resultado;
    }

    // Método para buscar todos os servidores efetivos com paginação
    public Page<ServidorEfetivo> getAllServidoresEfetivos(Pageable pageable) {
        return servidorEfetivoRepository.findAll(pageable);
    }

    // Método para buscar um servidor efetivo por ID
    public ServidorEfetivo getServidorEfetivoById(Long id) {
        return servidorEfetivoRepository.findById(id).orElse(null); // Retorna null se não encontrar
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
                servidorEfetivoDTO.getPai()
        );
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

        servidorB = servidorEfetivoRepository.save(servidorB);
        return servidorB;
    }
}
