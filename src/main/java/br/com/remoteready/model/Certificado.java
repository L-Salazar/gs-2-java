package br.com.remoteready.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TB_GS_CERTIFICADO")
public class Certificado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CERTIFICADO")
    private Long id;

    @Column(name = "ID_USUARIO", nullable = false)
    private Long idUsuario;

    @Column(name = "DT_EMISSAO")
    private LocalDate dataEmissao;

    @Column(name = "DS_TITULO", length = 100, columnDefinition = "VARCHAR2(100 BYTE) DEFAULT 'Remote Work Ready'")
    private String titulo;

    public Certificado() {}

    public Certificado(Long idUsuario, LocalDate dataEmissao, String titulo) {
        this.idUsuario = idUsuario;
        this.dataEmissao = dataEmissao;
        this.titulo = titulo;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}