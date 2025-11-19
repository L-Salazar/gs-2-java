package br.com.remoteready.dto;

public class UsuarioAptoDTO {

    private Long idUsuario;
    private String nome;
    private String email;
    private Long postsLidos;
    private String statusCertificado; // "JÁ POSSUI" ou "PODE EMITIR"

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

    public Long getPostsLidos() {
        return postsLidos;
    }

    public void setPostsLidos(Long postsLidos) {
        this.postsLidos = postsLidos;
    }

    public String getStatusCertificado() {
        return statusCertificado;
    }

    public void setStatusCertificado(String statusCertificado) {
        this.statusCertificado = statusCertificado;
    }
}
