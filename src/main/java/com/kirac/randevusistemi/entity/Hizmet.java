package com.kirac.randevusistemi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Hizmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ad;
    private String aciklama;
    private Integer sure;
    private Double ucret;
    private Boolean aktif;

    // Boş Constructor
    public Hizmet() {

    }

    // Getter ve Setter - id
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Getter ve Setter - ad
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    // Getter ve Setter - aciklama
    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    // Getter ve Setter - sure
    public Integer getSure() {
        return sure;
    }

    public void setSure(Integer sure) {
        this.sure = sure;
    }

    // Getter ve Setter - ucret
    public Double getUcret() {
        return ucret;
    }

    public void setUcret(Double ucret) {
        this.ucret = ucret;
    }

    // Getter ve Setter - aktif
    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }
}