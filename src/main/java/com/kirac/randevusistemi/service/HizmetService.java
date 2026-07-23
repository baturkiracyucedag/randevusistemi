package com.kirac.randevusistemi.service;

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
                "Hizmet süresi sıfırdan büyük olmalıdır."
            );
        }

        if (hizmet.getUcret() == null || hizmet.getUcret() < 0) {
            throw new IllegalArgumentException(
                "Hizmet ücreti negatif olamaz."
            );
        }

        if (hizmet.getAktif() == null) {
            hizmet.setAktif(true);
        }

        return hizmetRepository.save(hizmet);
    }
}