package com.kirac.randevusistemi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kirac.randevusistemi.entity.Hizmet;
import com.kirac.randevusistemi.repository.HizmetRepository;

@Service
public class HizmetService {

    private final HizmetRepository hizmetRepository;

    public HizmetService(HizmetRepository hizmetRepository) {
        this.hizmetRepository = hizmetRepository;
    }

    public Hizmet hizmetEkle(Hizmet hizmet) {

        if (hizmet.getAd() == null || hizmet.getAd().isBlank()) {
            throw new IllegalArgumentException("Hizmet adı boş olamaz.");
        }

        if (hizmet.getSure() == null || hizmet.getSure() <= 0) {
            throw new IllegalArgumentException(
                    "Hizmet süresi sıfırdan büyük olmalıdır.");
        }

        if (hizmet.getUcret() == null || hizmet.getUcret() < 0) {
            throw new IllegalArgumentException(
                    "Hizmet ücreti negatif olamaz.");
        }

        if (hizmet.getAktif() == null) {
            hizmet.setAktif(true);
        }

        return hizmetRepository.save(hizmet);
    }

    public List<Hizmet> tumHizmetleriGetir() {
        return hizmetRepository.findAll();
    }

    public Hizmet idIleHizmetGetir(Integer id) {
        return hizmetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hizmet bulunamadı."));
    }

    public Hizmet hizmetGuncelle(Integer id, Hizmet yeniHizmet) {

        Hizmet mevcutHizmet = hizmetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hizmet bulunamadı."));

        if (yeniHizmet.getAd() == null || yeniHizmet.getAd().isBlank()) {
            throw new IllegalArgumentException("Hizmet adı boş olamaz.");
        }

        if (yeniHizmet.getSure() == null || yeniHizmet.getSure() <= 0) {
            throw new IllegalArgumentException(
                    "Hizmet süresi sıfırdan büyük olmalıdır.");
        }

        if (yeniHizmet.getUcret() == null || yeniHizmet.getUcret() < 0) {
            throw new IllegalArgumentException(
                    "Hizmet ücreti negatif olamaz.");
        }

        mevcutHizmet.setAd(yeniHizmet.getAd());
        mevcutHizmet.setAciklama(yeniHizmet.getAciklama());
        mevcutHizmet.setSure(yeniHizmet.getSure());
        mevcutHizmet.setUcret(yeniHizmet.getUcret());
        mevcutHizmet.setAktif(yeniHizmet.getAktif());

        return hizmetRepository.save(mevcutHizmet);
    }

    public void hizmetSil(Integer id) {

        Hizmet hizmet = hizmetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Hizmet bulunamadı."));

        hizmetRepository.delete(hizmet);
    }
}