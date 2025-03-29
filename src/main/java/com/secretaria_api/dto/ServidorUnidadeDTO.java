package com.secretaria_api.dto;

import java.util.List;

public class ServidorUnidadeDTO {
    private String nome;
    private Integer idade;
    private String unidadeLotacao;
    private List<String> urlFotos;

    public ServidorUnidadeDTO() {
    }

    public ServidorUnidadeDTO(String nome, Integer idade, String unidadeLotacao, List<String> urlFotos) {
        this.nome = nome;
        this.idade = idade;
        this.unidadeLotacao = unidadeLotacao;
        this.urlFotos = urlFotos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getUnidadeLotacao() {
        return unidadeLotacao;
    }

    public void setUnidadeLotacao(String unidadeLotacao) {
        this.unidadeLotacao = unidadeLotacao;
    }

    public List<String> getUrlFotos() {
        return urlFotos;
    }

    public void setUrlFotos(List<String> urlFotos) {
        this.urlFotos = urlFotos;
    }
}