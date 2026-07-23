package com.kirac.randevusistemi.entity;

import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Personel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ad;
    private String soyad;
    private String telefon;
    private String unvan;

    private LocalTime calismaBaslangic;
    private LocalTime calismaBitis;

    private Boolean aktif = true;

    public Personel() {
    }

    public Personel(
            Integer id,
            String ad,
            String soyad,
            String telefon,
            String unvan,
            LocalTime calismaBaslangic,
            LocalTime calismaBitis,
            Boolean aktif) {

        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.telefon = telefon;
        this.unvan = unvan;
        this.calismaBaslangic = calismaBaslangic;
        this.calismaBitis = calismaBitis;
        this.aktif = aktif;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getUnvan() {
        return unvan;
    }

    public void setUnvan(String unvan) {
        this.unvan = unvan;
    }

    public LocalTime getCalismaBaslangic() {
        return calismaBaslangic;
    }

    public void setCalismaBaslangic(LocalTime calismaBaslangic) {
        this.calismaBaslangic = calismaBaslangic;
    }

    public LocalTime getCalismaBitis() {
        return calismaBitis;
    }

    public void setCalismaBitis(LocalTime calismaBitis) {
        this.calismaBitis = calismaBitis;
    }

    public Boolean getAktif() {
        return aktif;
    }

    public void setAktif(Boolean aktif) {
        this.aktif = aktif;
    }
}