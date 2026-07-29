package com.kirac.randevusistemi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirac.randevusistemi.entity.Musteri;
import com.kirac.randevusistemi.service.MusteriService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Müşteri İşlemleri",
        description = "Müşteri ekleme, listeleme, güncelleme, sorgulama ve silme işlemleri"
)
@RestController
@RequestMapping("/api/musteriler")
public class MusteriController {

    private final MusteriService musteriService;

    public MusteriController(
            MusteriService musteriService) {

        this.musteriService = musteriService;
    }

    @Operation(
            summary = "Yeni müşteri oluşturur",
            description = "Ad, soyad, telefon, e-posta ve aktiflik bilgileriyle sisteme yeni bir müşteri ekler."
    )
    @PostMapping
    public Musteri musteriEkle(
            @RequestBody Musteri musteri) {

        return musteriService.musteriEkle(musteri);
    }

    @Operation(
            summary = "Tüm müşterileri listeler",
            description = "Sistemde kayıtlı olan bütün müşteri kayıtlarını getirir."
    )
    @GetMapping
    public List<Musteri> tumMusterileriGetir() {

        return musteriService.tumMusterileriGetir();
    }

    @Operation(
            summary = "ID ile müşteri getirir",
            description = "Gönderilen müşteri kimliğine ait kayıtlı müşteri bilgilerini getirir."
    )
    @GetMapping("/{id}")
    public Musteri idIleMusteriGetir(
            @Parameter(
                    description = "Bilgileri getirilecek müşterinin kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        return musteriService.idIleMusteriGetir(id);
    }

    @Operation(
            summary = "Müşteri bilgilerini günceller",
            description = "Gönderilen kimliğe ait müşterinin ad, soyad, telefon, e-posta ve aktiflik bilgilerini günceller."
    )
    @PutMapping("/{id}")
    public Musteri musteriGuncelle(
            @Parameter(
                    description = "Bilgileri güncellenecek müşterinin kimliği",
                    example = "1"
            )
            @PathVariable Integer id,
            @RequestBody Musteri musteri) {

        return musteriService.musteriGuncelle(
                id,
                musteri);
    }

    @Operation(
            summary = "Müşteriyi siler",
            description = "Gönderilen kimliğe ait müşteri kaydını sistemden siler."
    )
    @DeleteMapping("/{id}")
    public void musteriSil(
            @Parameter(
                    description = "Silinecek müşterinin kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        musteriService.musteriSil(id);
    }
}