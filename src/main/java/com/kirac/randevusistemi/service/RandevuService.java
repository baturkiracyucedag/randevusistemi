package com.kirac.randevusistemi.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kirac.randevusistemi.dto.MusaitSaatDto;
import com.kirac.randevusistemi.entity.Hizmet;
import com.kirac.randevusistemi.entity.Kullanici;
import com.kirac.randevusistemi.entity.Musteri;
import com.kirac.randevusistemi.entity.Personel;
import com.kirac.randevusistemi.entity.Randevu;
import com.kirac.randevusistemi.repository.HizmetRepository;
import com.kirac.randevusistemi.repository.KullaniciRepository;
import com.kirac.randevusistemi.repository.MusteriRepository;
import com.kirac.randevusistemi.repository.PersonelRepository;
import com.kirac.randevusistemi.repository.RandevuRepository;

@Service
public class RandevuService {

        private final RandevuRepository randevuRepository;
        private final PersonelRepository personelRepository;
        private final MusteriRepository musteriRepository;
        private final HizmetRepository hizmetRepository;
        private final KullaniciRepository kullaniciRepository;

        public RandevuService(
                        RandevuRepository randevuRepository,
                        PersonelRepository personelRepository,
                        MusteriRepository musteriRepository,
                        HizmetRepository hizmetRepository,
                        KullaniciRepository kullaniciRepository) {

                this.randevuRepository = randevuRepository;
                this.personelRepository = personelRepository;
                this.musteriRepository = musteriRepository;
                this.hizmetRepository = hizmetRepository;
                this.kullaniciRepository = kullaniciRepository;
        }

        public Randevu randevuEkle(Randevu randevu) {

                if (randevu.getTarih() == null) {
                        throw new IllegalArgumentException(
                                        "Randevu tarihi boş bırakılamaz.");
                }

                if (randevu.getSaat() == null) {
                        throw new IllegalArgumentException(
                                        "Randevu saati boş bırakılamaz.");
                }

                if (randevu.getPersonel() == null
                                || randevu.getPersonel().getId() == null) {

                        throw new IllegalArgumentException(
                                        "Personel seçilmelidir.");
                }

                if (randevu.getMusteri() == null
                                || randevu.getMusteri().getId() == null) {

                        throw new IllegalArgumentException(
                                        "Müşteri seçilmelidir.");
                }

                if (randevu.getHizmet() == null
                                || randevu.getHizmet().getId() == null) {

                        throw new IllegalArgumentException(
                                        "Hizmet seçilmelidir.");
                }
                if (randevu.getTarih().isBefore(LocalDate.now())) {
                        throw new IllegalArgumentException(
                                        "Geçmiş bir tarihe randevu oluşturulamaz.");
                }

                Personel personel = personelRepository
                                .findById(randevu.getPersonel().getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Personel bulunamadı."));

                Musteri musteri = musteriRepository
                                .findById(randevu.getMusteri().getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Müşteri bulunamadı."));

                Hizmet hizmet = hizmetRepository
                                .findById(randevu.getHizmet().getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Hizmet bulunamadı."));

                if (hizmet.getSure() == null) {
                        throw new IllegalArgumentException(
                                        "Seçilen hizmetin süresi tanımlanmamış.");
                }

                LocalTime randevuBitisSaati = randevu.getSaat()
                                .plusMinutes(hizmet.getSure());

                if (randevu.getSaat()
                                .isBefore(personel.getCalismaBaslangic())
                                || randevuBitisSaati
                                                .isAfter(personel.getCalismaBitis())) {

                        throw new IllegalArgumentException(
                                        "Randevu, personelin çalışma saatleri içerisinde tamamlanmalıdır.");
                }

                /*
                 * Postman'dan gelen nesnelerde yalnızca ID bulunur.
                 * Çakışma kontrolünden önce veritabanından bulunan tam nesneleri
                 * randevuya yerleştiriyoruz.
                 */
                randevu.setPersonel(personel);
                randevu.setMusteri(musteri);
                randevu.setHizmet(hizmet);

                if (randevuCakisiyorMu(randevu, null)) {
                        throw new IllegalArgumentException(
                                        "Seçilen personelin bu saat aralığında başka bir randevusu bulunmaktadır.");
                }

                if (randevu.getDurum() == null
                                || randevu.getDurum().isBlank()) {

                        randevu.setDurum("BEKLIYOR");
                }

                return randevuRepository.save(randevu);
        }

        public Randevu kullaniciRandevusuEkle(
                        Randevu randevu,
                        String kullaniciAdi) {

                Kullanici kullanici = kullaniciRepository
                                .findByKullaniciAdi(kullaniciAdi)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Kullanıcı bulunamadı."));

                Musteri musteri = kullanici.getMusteri();

                if (musteri == null) {
                        throw new IllegalArgumentException(
                                        "Kullanıcıya bağlı müşteri kaydı bulunamadı.");
                }

                randevu.setMusteri(musteri);

                return randevuEkle(randevu);
        }

        public List<Randevu> tumRandevulariGetir() {
                return randevuRepository.findAll();
        }

        public List<Randevu> kullanicininRandevulariniGetir(
                        String kullaniciAdi) {

                Kullanici kullanici = kullaniciRepository
                                .findByKullaniciAdi(kullaniciAdi)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Kullanıcı bulunamadı."));

                Musteri musteri = kullanici.getMusteri();

                if (musteri == null) {
                        throw new IllegalArgumentException(
                                        "Kullanıcıya bağlı müşteri kaydı bulunamadı.");
                }

                return randevuRepository
                                .findByMusteriIdOrderByTarihAscSaatAsc(
                                                musteri.getId());
        }

        public Randevu idIleRandevuGetir(Integer id) {
                return randevuRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Randevu bulunamadı."));
        }

        public Randevu randevuGuncelle(
                        Integer id,
                        Randevu yeniRandevu) {

                Randevu mevcutRandevu = randevuRepository
                                .findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Randevu bulunamadı."));

                if (yeniRandevu.getTarih() == null) {
                        throw new IllegalArgumentException(
                                        "Randevu tarihi boş bırakılamaz.");
                }

                if (yeniRandevu.getSaat() == null) {
                        throw new IllegalArgumentException(
                                        "Randevu saati boş bırakılamaz.");
                }

                if (yeniRandevu.getPersonel() == null
                                || yeniRandevu.getPersonel().getId() == null) {

                        throw new IllegalArgumentException(
                                        "Personel seçilmelidir.");
                }

                if (yeniRandevu.getMusteri() == null
                                || yeniRandevu.getMusteri().getId() == null) {

                        throw new IllegalArgumentException(
                                        "Müşteri seçilmelidir.");
                }

                if (yeniRandevu.getHizmet() == null
                                || yeniRandevu.getHizmet().getId() == null) {

                        throw new IllegalArgumentException(
                                        "Hizmet seçilmelidir.");
                }
                if (yeniRandevu.getTarih().isBefore(LocalDate.now())) {
                        throw new IllegalArgumentException(
                                        "Geçmiş bir tarihe randevu alınamaz.");
                }

                Personel personel = personelRepository
                                .findById(yeniRandevu.getPersonel().getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Personel bulunamadı."));

                Musteri musteri = musteriRepository
                                .findById(yeniRandevu.getMusteri().getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Müşteri bulunamadı."));

                Hizmet hizmet = hizmetRepository
                                .findById(yeniRandevu.getHizmet().getId())
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Hizmet bulunamadı."));

                if (hizmet.getSure() == null) {
                        throw new IllegalArgumentException(
                                        "Seçilen hizmetin süresi tanımlanmamış.");
                }

                LocalTime randevuBitisSaati = yeniRandevu.getSaat()
                                .plusMinutes(hizmet.getSure());

                if (yeniRandevu.getSaat()
                                .isBefore(personel.getCalismaBaslangic())
                                || randevuBitisSaati
                                                .isAfter(personel.getCalismaBitis())) {

                        throw new IllegalArgumentException(
                                        "Randevu, personelin çalışma saatleri içerisinde tamamlanmalıdır.");
                }

                /*
                 * Çakışma kontrolünün hizmet süresini kullanabilmesi için
                 * veritabanından bulunan tam nesneleri yeni randevuya atıyoruz.
                 */
                yeniRandevu.setPersonel(personel);
                yeniRandevu.setMusteri(musteri);
                yeniRandevu.setHizmet(hizmet);

                /*
                 * id parametresi sayesinde güncellenen randevu,
                 * kendi kaydıyla çakışıyor kabul edilmez.
                 */
                if (randevuCakisiyorMu(yeniRandevu, id)) {
                        throw new IllegalArgumentException(
                                        "Seçilen personelin bu saat aralığında başka bir randevusu bulunmaktadır.");
                }

                mevcutRandevu.setTarih(yeniRandevu.getTarih());
                mevcutRandevu.setSaat(yeniRandevu.getSaat());
                mevcutRandevu.setPersonel(personel);
                mevcutRandevu.setMusteri(musteri);
                mevcutRandevu.setHizmet(hizmet);

                if (yeniRandevu.getDurum() == null
                                || yeniRandevu.getDurum().isBlank()) {

                        mevcutRandevu.setDurum("BEKLIYOR");
                } else {
                        mevcutRandevu.setDurum(
                                        yeniRandevu.getDurum());
                }

                return randevuRepository.save(mevcutRandevu);
        }

        public void randevuSil(Integer id) {

                Randevu randevu = randevuRepository
                                .findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Randevu bulunamadı."));

                randevuRepository.delete(randevu);
        }

        public Randevu kullaniciRandevusunuIptalEt(
                        Integer randevuId,
                        String kullaniciAdi) {

                Kullanici kullanici = kullaniciRepository
                                .findByKullaniciAdi(kullaniciAdi)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Kullanıcı bulunamadı."));

                Musteri musteri = kullanici.getMusteri();

                if (musteri == null) {
                        throw new IllegalArgumentException(
                                        "Kullanıcıya bağlı müşteri kaydı bulunamadı.");
                }

                Randevu randevu = randevuRepository
                                .findById(randevuId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Randevu bulunamadı."));

                if (randevu.getMusteri() == null
                                || !randevu.getMusteri()
                                                .getId()
                                                .equals(musteri.getId())) {

                        throw new IllegalArgumentException(
                                        "Bu randevuyu iptal etme yetkiniz bulunmamaktadır.");
                }

                if ("IPTAL_EDILDI".equals(randevu.getDurum())) {
                        throw new IllegalArgumentException(
                                        "Bu randevu zaten iptal edilmiş.");
                }

                if ("TAMAMLANDI".equals(randevu.getDurum())) {
                        throw new IllegalArgumentException(
                                        "Tamamlanmış bir randevu iptal edilemez.");
                }

                randevu.setDurum("IPTAL_EDILDI");

                return randevuRepository.save(randevu);
        }

        public List<LocalTime> musaitSaatleriGetir(
                        Integer personelId,
                        LocalDate tarih,
                        Integer hizmetId) {

                List<MusaitSaatDto> saatDurumlari = tumSaatDurumlariniGetir(
                                personelId,
                                tarih,
                                hizmetId);

                List<LocalTime> musaitSaatler = new ArrayList<>();

                for (MusaitSaatDto saatDurumu : saatDurumlari) {

                        if (saatDurumu.isMusait()) {
                                musaitSaatler.add(
                                                saatDurumu.getSaat());
                        }
                }

                return musaitSaatler;
        }

        public List<MusaitSaatDto> tumSaatDurumlariniGetir(
                        Integer personelId,
                        LocalDate tarih,
                        Integer hizmetId) {

                if (personelId == null) {
                        throw new IllegalArgumentException(
                                        "Personel seçilmelidir.");
                }

                if (tarih == null) {
                        throw new IllegalArgumentException(
                                        "Tarih seçilmelidir.");
                }

                if (hizmetId == null) {
                        throw new IllegalArgumentException(
                                        "Hizmet seçilmelidir.");
                }

                Personel personel = personelRepository
                                .findById(personelId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Personel bulunamadı."));

                Hizmet hizmet = hizmetRepository
                                .findById(hizmetId)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Hizmet bulunamadı."));

                if (personel.getCalismaBaslangic() == null
                                || personel.getCalismaBitis() == null) {

                        throw new IllegalArgumentException(
                                        "Personelin çalışma saatleri tanımlanmamış.");
                }

                if (hizmet.getSure() == null
                                || hizmet.getSure() <= 0) {

                        throw new IllegalArgumentException(
                                        "Seçilen hizmetin süresi geçersiz.");
                }

                List<MusaitSaatDto> saatDurumlari = new ArrayList<>();

                LocalTime kontrolSaati = personel.getCalismaBaslangic();

                LocalTime calismaBitis = personel.getCalismaBitis();

                int zamanAraligi = 30;

                while (!kontrolSaati
                                .plusMinutes(hizmet.getSure())
                                .isAfter(calismaBitis)) {

                        Randevu geciciRandevu = new Randevu();

                        geciciRandevu.setTarih(tarih);
                        geciciRandevu.setSaat(kontrolSaati);
                        geciciRandevu.setPersonel(personel);
                        geciciRandevu.setHizmet(hizmet);

                        boolean musait = !randevuCakisiyorMu(
                                        geciciRandevu,
                                        null);

                        saatDurumlari.add(
                                        new MusaitSaatDto(
                                                        kontrolSaati,
                                                        musait));

                        kontrolSaati = kontrolSaati.plusMinutes(
                                        zamanAraligi);
                }

                return saatDurumlari;
        }

        private boolean randevuCakisiyorMu(
                        Randevu yeniRandevu,
                        Integer haricTutulacakRandevuId) {

                LocalTime yeniBaslangic = yeniRandevu.getSaat();

                Integer yeniHizmetSuresi = yeniRandevu.getHizmet().getSure();

                if (yeniHizmetSuresi == null) {
                        throw new IllegalArgumentException(
                                        "Seçilen hizmetin süresi tanımlanmamış.");
                }

                LocalTime yeniBitis = yeniBaslangic
                                .plusMinutes(yeniHizmetSuresi);

                List<Randevu> mevcutRandevular = randevuRepository.findByPersonelIdAndTarih(
                                yeniRandevu.getPersonel().getId(),
                                yeniRandevu.getTarih());

                for (Randevu mevcutRandevu : mevcutRandevular) {
                        if ("IPTAL_EDILDI".equals(mevcutRandevu.getDurum())) {
                                continue;
                        }

                        if (haricTutulacakRandevuId != null
                                        && mevcutRandevu.getId()
                                                        .equals(haricTutulacakRandevuId)) {

                                continue;
                        }

                        LocalTime mevcutBaslangic = mevcutRandevu.getSaat();

                        Integer mevcutHizmetSuresi = mevcutRandevu.getHizmet().getSure();

                        if (mevcutHizmetSuresi == null) {
                                throw new IllegalArgumentException(
                                                "Mevcut randevuya ait hizmetin süresi tanımlanmamış.");
                        }

                        LocalTime mevcutBitis = mevcutBaslangic
                                        .plusMinutes(mevcutHizmetSuresi);

                        if (yeniBaslangic.isBefore(mevcutBitis)
                                        && yeniBitis.isAfter(mevcutBaslangic)) {

                                return true;
                        }
                }

                return false;
        }
}