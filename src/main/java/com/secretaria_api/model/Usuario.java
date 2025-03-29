package com.secretaria_api.model;

import com.secretaria_api.dto.UsuarioDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "user_login", nullable = false, length = 20)
    private String login;

    @Column(name = "user_senha", nullable = false, length = 20)
    private String senha;

    @Column(name = "user_email", length = 200)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioDTO toDTO() {
        return new UsuarioDTO(
                this.id,
                this.nome,
                this.login,
                this.email
        );
    }
}
