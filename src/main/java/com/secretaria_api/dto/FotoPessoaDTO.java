package com.secretaria_api.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FotoPessoaDTO {
    private Long pessoaId;
    private LocalDate data;
    private String bucket;
    private String hash;

    public FotoPessoaDTO() {
    }
}
