package com.secretaria_api.model;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "servidor_temporario")
public class ServidorTemporario {

    @Id
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id")
    private Long pesId; // Usando o ID de 'Pessoa' como chave primária de 'ServidorTemporario'

    @Column(name = "st_data_admissao")
    private LocalDate dataAdmissao;

    @Column(name = "st_data_demissao")
    private LocalDate dataDemissao;

    // Relacionamento com 'Pessoa'
    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id")
    private Pessoa pessoa;

    public ServidorTemporario() {
    }

    public Long getPesId() {
        return pesId;
    }

    public void setPesId(Long pesId) {
        this.pesId = pesId;
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

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
