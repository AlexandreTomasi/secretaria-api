package com.secretaria_api.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "unidade_endereco")
@IdClass(UnidadeEnderecoId.class)
public class UnidadeEndereco {

    @Id
    @ManyToOne
    @JoinColumn(name = "unid_id", referencedColumnName = "unid_id")
    private Unidade unidade;

    @Id
    @ManyToOne
    @JoinColumn(name = "end_id", referencedColumnName = "end_id")
    private Endereco endereco;
}
