package com.secretaria_api.dto;

public class EnderecoFuncionalDTO {
    private String nomeServidor;
    private String matricula;
    private String unidadeLotacao;
    private String siglaUnidade;
    private String tipoLogradouro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private String cidade;
    private String uf;

    public String getNomeServidor() {
        return nomeServidor;
    }

    public void setNomeServidor(String nomeServidor) {
        this.nomeServidor = nomeServidor;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getUnidadeLotacao() {
        return unidadeLotacao;
    }

    public void setUnidadeLotacao(String unidadeLotacao) {
        this.unidadeLotacao = unidadeLotacao;
    }

    public String getSiglaUnidade() {
        return siglaUnidade;
    }

    public void setSiglaUnidade(String siglaUnidade) {
        this.siglaUnidade = siglaUnidade;
    }

    public String getTipoLogradouro() {
        return tipoLogradouro;
    }

    public void setTipoLogradouro(String tipoLogradouro) {
        this.tipoLogradouro = tipoLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public EnderecoFuncionalDTO() {
    }

    public EnderecoFuncionalDTO(String nomeServidor, String matricula, String unidadeLotacao, String siglaUnidade, String tipoLogradouro, String logradouro, Integer numero, String bairro, String cidade, String uf) {
        this.nomeServidor = nomeServidor;
        this.matricula = matricula;
        this.unidadeLotacao = unidadeLotacao;
        this.siglaUnidade = siglaUnidade;
        this.tipoLogradouro = tipoLogradouro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public static EnderecoFuncionalDTO fromObjectArray(Object[] result) {
        return new EnderecoFuncionalDTO(
                (String) result[0], // nome_servidor
                (String) result[1], // matricula
                (String) result[2], // unidade_lotacao
                (String) result[3], // sigla_unidade
                (String) result[4], // tipo_logradouro
                (String) result[5], // logradouro
                (Integer) result[6], // numero
                (String) result[7], // bairro
                (String) result[8], // cidade
                (String) result[9]  // uf
        );
    }
}
