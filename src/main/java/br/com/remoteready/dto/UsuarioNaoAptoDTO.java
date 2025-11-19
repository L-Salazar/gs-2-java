package br.com.remoteready.dto;

public class UsuarioNaoAptoDTO {

    private Long idUsuario;
    private String nome;
    private Long postsLidos;
    private Long faltam;

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

    public Long getPostsLidos() {
        return postsLidos;
    }

    public void setPostsLidos(Long postsLidos) {
        this.postsLidos = postsLidos;
    }

    public Long getFaltam() {
        return faltam;
    }

    public void setFaltam(Long faltam) {
        this.faltam = faltam;
    }
}
