package com.kirac.randevusistemi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kirac.randevusistemi.entity.Personel;
import com.kirac.randevusistemi.repository.PersonelRepository;

@Service
public class PersonelService {

    private final PersonelRepository personelRepository;

    public PersonelService(PersonelRepository personelRepository) {
        this.personelRepository = personelRepository;
    }

    public Personel personelEkle(Personel personel) {

        if (personel.getAd() == null || personel.getAd().isBlank()) {
            throw new IllegalArgumentException(
                    "Personel adı boş olamaz."
            );
        }

        if (personel.getSoyad() == null || personel.getSoyad().isBlank()) {
            throw new IllegalArgumentException(
                    "Personel soyadı boş olamaz."
            );
        }

        if (personel.getUnvan() == null || personel.getUnvan().isBlank()) {
            throw new IllegalArgumentException(
                    "Personel unvanı boş olamaz."
            );
        }

        if (personel.getTelefon() == null || personel.getTelefon().isBlank()) {
            throw new IllegalArgumentException(
                    "Telefon bilgisi boş olamaz."
            );
        }

        if (personel.getCalismaBaslangic() == null
                || personel.getCalismaBitis() == null) {

            throw new IllegalArgumentException(
                    "Çalışma saatleri boş olamaz."
            );
        }

        if (!personel.getCalismaBaslangic()
                .isBefore(personel.getCalismaBitis())) {

            throw new IllegalArgumentException(
                    "Çalışma başlangıç saati, bitiş saatinden önce olmalıdır."
            );
        }

        if (personel.getAktif() == null) {
            personel.setAktif(true);
        }

        return personelRepository.save(personel);
    }

    public List<Personel> tumPersonelleriGetir() {
        return personelRepository.findAll();
    }

    public Personel idIlePersonelGetir(Integer id) {
        return personelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Personel bulunamadı."
                ));
    }

    public Personel personelGuncelle(Integer id, Personel yeniPersonel) {

        Personel mevcutPersonel = personelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Personel bulunamadı."
                ));

        if (yeniPersonel.getAd() == null || yeniPersonel.getAd().isBlank()) {
            throw new IllegalArgumentException(
                    "Personel adı boş olamaz."
            );
        }

        if (yeniPersonel.getSoyad() == null || yeniPersonel.getSoyad().isBlank()) {
            throw new IllegalArgumentException(
                    "Personel soyadı boş olamaz."
            );
        }

        if (yeniPersonel.getUnvan() == null || yeniPersonel.getUnvan().isBlank()) {
            throw new IllegalArgumentException(
                    "Personel unvanı boş olamaz."
            );
        }

        if (yeniPersonel.getTelefon() == null || yeniPersonel.getTelefon().isBlank()) {
            throw new IllegalArgumentException(
                    "Telefon bilgisi boş olamaz."
            );
        }

        if (yeniPersonel.getCalismaBaslangic() == null
                || yeniPersonel.getCalismaBitis() == null) {

            throw new IllegalArgumentException(
                    "Çalışma saatleri boş olamaz."
            );
        }

        if (!yeniPersonel.getCalismaBaslangic()
                .isBefore(yeniPersonel.getCalismaBitis())) {

            throw new IllegalArgumentException(
                    "Çalışma başlangıç saati, bitiş saatinden önce olmalıdır."
            );
        }

        mevcutPersonel.setAd(yeniPersonel.getAd());
        mevcutPersonel.setSoyad(yeniPersonel.getSoyad());
        mevcutPersonel.setUnvan(yeniPersonel.getUnvan());
        mevcutPersonel.setTelefon(yeniPersonel.getTelefon());
        mevcutPersonel.setCalismaBaslangic(yeniPersonel.getCalismaBaslangic());
        mevcutPersonel.setCalismaBitis(yeniPersonel.getCalismaBitis());

        if (yeniPersonel.getAktif() == null) {
            mevcutPersonel.setAktif(true);
        } else {
            mevcutPersonel.setAktif(yeniPersonel.getAktif());
        }

        return personelRepository.save(mevcutPersonel);
    }
    public void personelSil(Integer id) {

    Personel mevcutPersonel = personelRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException(
                    "Personel bulunamadı."
            ));

    personelRepository.delete(mevcutPersonel);
}
}