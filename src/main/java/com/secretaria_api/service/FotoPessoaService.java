package com.secretaria_api.service;

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

            String hash = minioService.uploadFile(fotoPessoa.getId(), file);
            fotoPessoa.setHash(hash);
            list.add(fotoPessoaRepository.save(fotoPessoa));
            fotoPessoa.setUrl(minioService.gerarLinkDownload(hash));
        }
        return list;
    }

    public List<FotoPessoa> createUma(Long pessoaId, MultipartFile arquivo) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));

        try {
            List<FotoPessoa> list = new ArrayList<>();
            FotoPessoa fotoPessoa = new FotoPessoa();
            fotoPessoa.setPessoa(pessoa);
            fotoPessoa.setData(LocalDate.now());
            fotoPessoa.setBucket(bucketName);
            fotoPessoa = fotoPessoaRepository.save(fotoPessoa);

            String hash = minioService.uploadFile(fotoPessoa.getId(), arquivo);
            fotoPessoa.setHash(hash);
            list.add(fotoPessoaRepository.save(fotoPessoa));
            fotoPessoa.setUrl(minioService.gerarLinkDownload(hash));
            return list;
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public FotoPessoa buscaFoto(Long fotoId){
        FotoPessoa fotoPessoa = fotoPessoaRepository.findById(fotoId).orElse(null);
        if(fotoPessoa == null){
            throw new RuntimeException("foto não encontrada");
        }
        fotoPessoa.setUrl(minioService.gerarLinkDownload(fotoPessoa.getHash()));
        return fotoPessoa;
    }

    public List<String> gerarListaLinkVariasFotosPessoa(List<FotoPessoa> fotos){
        List<String> resposta = new ArrayList<>();
        if(fotos != null) {
            for (FotoPessoa foto : fotos) {
                resposta.add(minioService.gerarLinkDownload(foto.getHash()));
            }
        }
        return resposta;
    }

    public List<FotoPessoa> gerarLinkVariasFotosPessoa2(List<FotoPessoa> fotos){
        if(fotos != null) {
            for (FotoPessoa foto : fotos) {
                foto.setUrl(minioService.gerarLinkDownload(foto.getHash()));
            }
        }
        return fotos;
    }
}
