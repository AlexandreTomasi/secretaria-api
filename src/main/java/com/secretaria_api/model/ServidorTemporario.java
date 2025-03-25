package com.secretaria_api.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
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
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", insertable = false, updatable = false)
    private Pessoa pessoa;
}
