package br.com.remoteready.dto;

import java.util.List;

public class ChatHistoryResult {

    private Long idUsuario;
    private String nome;
    private String email;
    private List<ChatMensagemDTO> mensagens;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<ChatMensagemDTO> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<ChatMensagemDTO> mensagens) {
        this.mensagens = mensagens;
    }
}
