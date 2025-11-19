package br.com.remoteready.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_GS_BLOG_POST")
public class BlogPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_POST")
    private Long id;

    @Column(name = "DS_TITULO", length = 120, nullable = false)
    private String titulo;

    @Column(name = "DS_DESCRICAO", length = 600)
    private String descricao;

    @Column(name = "DS_IMAGE_URL", length = 300)
    private String imageUrl;

    @Column(name = "DS_TAG", length = 50)
    private String tag;

    @Column(name = "DT_CRIACAO")
    private LocalDateTime dataCriacao;

    public BlogPost() {}

    public BlogPost(String titulo, String descricao, String imageUrl, String tag,
                    LocalDateTime dataCriacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.imageUrl = imageUrl;
        this.tag = tag;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}