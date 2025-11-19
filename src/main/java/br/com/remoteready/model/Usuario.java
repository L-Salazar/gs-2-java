package br.com.remoteready.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_GS_USUARIO")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private Long id;

    @Column(name = "NM_USUARIO", length = 100, nullable = false)
    private String nomeUsuario;

    @Column(name = "DS_EMAIL", length = 120, nullable = false)
    private String email;

    @Column(name = "DS_PASSWORD", length = 60, nullable = false)
    private String password;

    @Column(name = "TP_ROLE", length = 20, nullable = false, columnDefinition = "VARCHAR2(20 BYTE) DEFAULT 'USER'")
    private String role;

    @Column(name = "DT_CRIACAO", insertable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public Usuario() {}

    public Usuario(String nomeUsuario, String email, String password, String role, LocalDateTime dataCriacao) {
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.password = password;
        this.role = role;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}