package com.kirac.randevusistemi.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "randevular")
public class Randevu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate tarih;

    private LocalTime saat;

    private String durum;

    @ManyToOne
    @JoinColumn(name = "personel_id")
    private Personel personel;

    @ManyToOne
    @JoinColumn(name = "musteri_id")
    private Musteri musteri;

    @ManyToOne
    @JoinColumn(name = "hizmet_id")
    private Hizmet hizmet;

    public Randevu() {
    }

   public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public LocalDate getTarih() {
    return tarih;
}

public void setTarih(LocalDate tarih) {
    this.tarih = tarih;
}

public LocalTime getSaat() {
    return saat;
}

public void setSaat(LocalTime saat) {
    this.saat = saat;
}

public String getDurum() {
    return durum;
}

public void setDurum(String durum) {
    this.durum = durum;
}

public Personel getPersonel() {
    return personel;
}

public void setPersonel(Personel personel) {
    this.personel = personel;
}

public Musteri getMusteri() {
    return musteri;
}

public void setMusteri(Musteri musteri) {
    this.musteri = musteri;
}

public Hizmet getHizmet() {
    return hizmet;
}

public void setHizmet(Hizmet hizmet) {
    this.hizmet = hizmet;
}
}