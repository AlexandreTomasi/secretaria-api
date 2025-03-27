package com.secretaria_api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class UnidadeEnderecoId implements Serializable {
    private Long unidade;
    private Long endereco;
}
