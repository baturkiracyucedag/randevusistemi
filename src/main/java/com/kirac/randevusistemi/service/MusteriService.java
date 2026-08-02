package com.kirac.randevusistemi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kirac.randevusistemi.entity.Musteri;
import com.kirac.randevusistemi.repository.MusteriRepository;

@Service
public class MusteriService {

    private final MusteriRepository musteriRepository;

    public MusteriService(MusteriRepository musteriRepository) {
        this.musteriRepository = musteriRepository;
    }

    public Musteri musteriEkle(Musteri musteri) {
        musteriDogrula(musteri);

        if (musteri.getAktif() == null) {
            musteri.setAktif(true);
        }

        return musteriRepository.save(musteri);
    }

    public List<Musteri> tumMusterileriGetir() {
        return musteriRepository.findAll();
    }

    public Musteri idIleMusteriGetir(Integer id) {
        return musteriRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Müşteri bulunamadı."));
    }

    public Musteri musteriGuncelle(Integer id, Musteri yeniMusteri) {
        Musteri mevcutMusteri = idIleMusteriGetir(id);

        musteriDogrula(yeniMusteri);

        mevcutMusteri.setAd(yeniMusteri.getAd());
        mevcutMusteri.setSoyad(yeniMusteri.getSoyad());
        mevcutMusteri.setTelefon(yeniMusteri.getTelefon());
        mevcutMusteri.setEmail(yeniMusteri.getEmail());

        if (yeniMusteri.getAktif() != null) {
            mevcutMusteri.setAktif(yeniMusteri.getAktif());
        }

        return musteriRepository.save(mevcutMusteri);
    }

    public void musteriSil(Integer id) {
        Musteri musteri = idIleMusteriGetir(id);
        musteriRepository.delete(musteri);
    }

    private void musteriDogrula(Musteri musteri) {
        if (musteri.getAd() == null || musteri.getAd().isBlank()) {
            throw new IllegalArgumentException("Müşteri adı boş olamaz.");
        }

        if (musteri.getSoyad() == null || musteri.getSoyad().isBlank()) {
            throw new IllegalArgumentException("Müşteri soyadı boş olamaz.");
        }

        if (musteri.getTelefon() == null || musteri.getTelefon().isBlank()) {
            throw new IllegalArgumentException("Telefon numarası boş olamaz.");
        }

        String telefon = musteri.getTelefon().replaceAll("\\D", "");

        if (telefon.length() < 10) {
            throw new IllegalArgumentException(
                    "Telefon numarası en az 10 haneli olmalıdır.");
        }

        if (musteri.getEmail() != null
                && !musteri.getEmail().isBlank()
                && !musteri.getEmail().contains("@")) {

            throw new IllegalArgumentException(
                    "Geçerli bir e-posta adresi giriniz.");
        }
    }
}