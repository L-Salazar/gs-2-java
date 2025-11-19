package br.com.remoteready.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_GS_USER_POST")
public class UserPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER_POST")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "ID_POST", nullable = false)
    private BlogPost blogPost;

    @Column(name = "DS_STATUS", length = 20, nullable = false)
    private String status;

    @Column(name = "DT_LEITURA", nullable = false)
    private LocalDateTime dataLeitura;

    public UserPost() {
    }

    public UserPost(Usuario usuario, BlogPost blogPost, String status, LocalDateTime dataLeitura) {
        this.usuario = usuario;
        this.blogPost = blogPost;
        this.status = status;
        this.dataLeitura = dataLeitura;
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BlogPost getBlogPost() {
        return blogPost;
    }

    public void setBlogPost(BlogPost blogPost) {
        this.blogPost = blogPost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(LocalDateTime dataLeitura) {
        this.dataLeitura = dataLeitura;
    }
}
