package br.com.remoteready.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_GS_CHAT_HISTORY")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CHAT")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @Lob
    @Column(name = "PROMPT", nullable = false)
    private String prompt;

    @Lob
    @Column(name = "RESPONSE")
    private String response;

    @Column(name = "DT_CRIACAO", insertable = false, updatable = false)
    private LocalDateTime dataCriacao;

    public ChatHistory() {
    }

    public ChatHistory(Usuario usuario, String prompt, String response) {
        this.usuario = usuario;
        this.prompt = prompt;
        this.response = response;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getResponse() {
        return response;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
