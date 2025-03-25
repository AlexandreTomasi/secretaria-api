package com.secretaria_api.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "servidor_efetivo")
public class ServidorEfetivo {

    @Id
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id")
    private Long pesId;  // Usando o ID de 'Pessoa' como chave primária de 'ServidorEfetivo'

    @Column(name = "se_matricula", nullable = false, length = 20)
    private String matricula;

    // Relacionamento com 'Pessoa'
    @OneToOne
    @JoinColumn(name = "pes_id", referencedColumnName = "pes_id", insertable = false, updatable = false)
    private Pessoa pessoa;
}
