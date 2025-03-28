package com.secretaria_api.service;

import com.secretaria_api.dto.FotoPessoaDTO;
import com.secretaria_api.model.FotoPessoa;
import com.secretaria_api.model.Pessoa;
import com.secretaria_api.repository.FotoPessoaRepository;
import com.secretaria_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FotoPessoaService {

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Autowired
    FotoPessoaRepository fotoPessoaRepository;

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    MinioService minioService;

    public List<FotoPessoa> create(Long pessoaId, MultipartFile[] arquivos) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        List<FotoPessoa> list = new ArrayList<>();
        for(MultipartFile file : arquivos) {
            FotoPessoa fotoPessoa = new FotoPessoa();
            fotoPessoa.setPessoa(pessoa);
            fotoPessoa.setData(LocalDate.now());
            fotoPessoa.setBucket(bucketName);
            fotoPessoa = fotoPessoaRepository.save(fotoPessoa);

            String hash = minioService.uploadFile(fotoPessoa.getId(), arquivos[0]);
            fotoPessoa.setHash(hash);
            list.add(fotoPessoaRepository.save(fotoPessoa));
        }
        return list;
    }

    public FotoPessoa buscaFoto(Long fotoId){
        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(fotoId).orElse(null);
        if(fotoPessoa == null){
            throw new RuntimeException("foto não encontrada");
        }
        fotoPessoa.setUrl(minioService.gerarLinkDownload(fotoPessoa.getHash()));
        return fotoPessoa;
    }
}
