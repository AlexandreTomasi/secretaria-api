package com.secretaria_api.dto;

import java.time.LocalDate;
import java.util.List;

public class ServidorTemporarioDTO {
    private Long pesId;

    private String nome;

    private LocalDate dataNascimento;

    private String sexo;

    private String mae;

    private String pai;

    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;

    private List<EnderecoDTO> enderecoDTO;

    public ServidorTemporarioDTO() {
    }

    public Long getPesId() {
        return pesId;
    }

    public void setPesId(Long pesId) {
        this.pesId = pesId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public LocalDate getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(LocalDate dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public List<EnderecoDTO> getEnderecoDTO() {
        return enderecoDTO;
    }

    public void setEnderecoDTO(List<EnderecoDTO> enderecoDTO) {
        this.enderecoDTO = enderecoDTO;
    }
}
