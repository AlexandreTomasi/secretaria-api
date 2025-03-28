package com.secretaria_api.dto;

public class UsuarioDTO {
    private Long id;
    private String nome;
    private String login;
    private String email;

    // Construtor
    public UsuarioDTO(Long id, String nome, String login, String email) {
        this.id = id;
        this.nome = nome;
        this.login = login;
        this.email = email;
    }

    // Getters (sem setters se quiser imutável)
    public Long getId() { return id; }
    public String getNome() { return nome; }
    public String getLogin() { return login; }
    public String getEmail() { return email; }
}