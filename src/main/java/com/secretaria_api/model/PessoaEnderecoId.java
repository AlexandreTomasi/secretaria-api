package com.secretaria_api.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class PessoaEnderecoId implements Serializable {
    private Long pessoa;
    private Long endereco;
}
