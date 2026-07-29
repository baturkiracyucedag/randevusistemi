package com.kirac.randevusistemi.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import com.kirac.randevusistemi.dto.MusaitSaatDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kirac.randevusistemi.entity.Randevu;
import com.kirac.randevusistemi.service.RandevuService;

@RestController
@RequestMapping("/api/randevular")
public class RandevuController {

    private final RandevuService randevuService;

    public RandevuController(RandevuService randevuService) {
        this.randevuService = randevuService;
    }

    @PostMapping
    public Randevu randevuEkle(
            @RequestBody Randevu randevu) {

        return randevuService.randevuEkle(randevu);
    }

    @GetMapping
    public List<Randevu> tumRandevulariGetir() {
        return randevuService.tumRandevulariGetir();
    }

    @GetMapping("/{id}")
    public Randevu idIleRandevuGetir(
            @PathVariable Integer id) {

        return randevuService.idIleRandevuGetir(id);
    }

    @PutMapping("/{id}")
    public Randevu randevuGuncelle(
            @PathVariable Integer id,
            @RequestBody Randevu randevu) {

        return randevuService.randevuGuncelle(
                id,
                randevu);
    }

    @DeleteMapping("/{id}")
    public void randevuSil(
            @PathVariable Integer id) {

        randevuService.randevuSil(id);
    }

    @GetMapping("/musait-saatler")
    public List<LocalTime> musaitSaatleriGetir(
            @RequestParam Integer personelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tarih,
            @RequestParam Integer hizmetId) {

        return randevuService.musaitSaatleriGetir(
                personelId,
                tarih,
                hizmetId);
    }

    @GetMapping("/saat-durumlari")
    public List<MusaitSaatDto> tumSaatDurumlariniGetir(
            @RequestParam Integer personelId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate tarih,
            @RequestParam Integer hizmetId) {

        return randevuService
                .tumSaatDurumlariniGetir(
                        personelId,
                        tarih,
                        hizmetId);
    }
}