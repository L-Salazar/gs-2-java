package br.com.remoteready.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_GS_EMPRESA")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPRESA")
    private Long id;

    @Column(name = "NM_EMPRESA", length = 120, nullable = false)
    private String nomeEmpresa;

    @Column(name = "DS_EMPRESA", length = 300)
    private String descricaoEmpresa;

    @Column(name = "DS_AREA", length = 60)
    private String area;

    @Column(name = "FL_HIRING_NOW", length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String hiringNow;

    @Column(name = "DS_LOGO_URL", length = 300)
    private String logoUrl;

    @Column(name = "DS_WEBSITE", length = 150)
    private String website;

    @Column(name = "DT_CRIACAO")
    private LocalDateTime dataCriacao;

    public Empresa() {}

    public Empresa(String nomeEmpresa, String descricaoEmpresa, String area, String hiringNow,
                   String logoUrl, String website, LocalDateTime dataCriacao) {
        this.nomeEmpresa = nomeEmpresa;
        this.descricaoEmpresa = descricaoEmpresa;
        this.area = area;
        this.hiringNow = hiringNow;
        this.logoUrl = logoUrl;
        this.website = website;
        this.dataCriacao = dataCriacao;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getDescricaoEmpresa() {
        return descricaoEmpresa;
    }

    public void setDescricaoEmpresa(String descricaoEmpresa) {
        this.descricaoEmpresa = descricaoEmpresa;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHiringNow() {
        return hiringNow;
    }

    public void setHiringNow(String hiringNow) {
        this.hiringNow = hiringNow;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}