package com.secretaria_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "servidor_efetivo")
public class ServidorEfetivo {

    @Id
    @Column(name = "pes_id")
    private Long pesId;  // Usando o ID de 'Pessoa' como chave primária de 'ServidorEfetivo'

    @Column(name = "se_matricula", nullable = false, length = 20)
    private String matricula;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id")
    private Pessoa pessoa;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Long getPesId() {
        return pesId;
    }

    public void setPesId(Long pesId) {
        this.pesId = pesId;
    }

    public ServidorEfetivo() {
    }


}
