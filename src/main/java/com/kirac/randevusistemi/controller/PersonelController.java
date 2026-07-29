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

import com.kirac.randevusistemi.entity.Personel;
import com.kirac.randevusistemi.service.PersonelService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Personel İşlemleri",
        description = "Personel ekleme, listeleme, güncelleme, sorgulama ve silme işlemleri"
)
@RestController
@RequestMapping("/api/personeller")
public class PersonelController {

    private final PersonelService personelService;

    public PersonelController(
            PersonelService personelService) {

        this.personelService = personelService;
    }

    @Operation(
            summary = "Yeni personel oluşturur",
            description = "Ad, soyad, telefon, unvan ve çalışma saatleri bilgileriyle sisteme yeni bir personel ekler."
    )
    @PostMapping
    public Personel personelEkle(
            @RequestBody Personel personel) {

        return personelService.personelEkle(personel);
    }

    @Operation(
            summary = "Tüm personelleri listeler",
            description = "Sistemde kayıtlı olan bütün personel kayıtlarını getirir."
    )
    @GetMapping
    public List<Personel> tumPersonelleriGetir() {

        return personelService.tumPersonelleriGetir();
    }

    @Operation(
            summary = "ID ile personel getirir",
            description = "Gönderilen personel kimliğine ait kayıtlı personel bilgilerini getirir."
    )
    @GetMapping("/{id}")
    public Personel idIlePersonelGetir(
            @Parameter(
                    description = "Bilgileri getirilecek personelin kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        return personelService.idIlePersonelGetir(id);
    }

    @Operation(
            summary = "Personel bilgilerini günceller",
            description = "Gönderilen kimliğe ait personelin iletişim, unvan, çalışma saatleri ve aktiflik bilgilerini günceller."
    )
    @PutMapping("/{id}")
    public Personel personelGuncelle(
            @Parameter(
                    description = "Bilgileri güncellenecek personelin kimliği",
                    example = "1"
            )
            @PathVariable Integer id,
            @RequestBody Personel personel) {

        return personelService.personelGuncelle(
                id,
                personel);
    }

    @Operation(
            summary = "Personeli siler",
            description = "Gönderilen kimliğe ait personel kaydını sistemden siler."
    )
    @DeleteMapping("/{id}")
    public void personelSil(
            @Parameter(
                    description = "Silinecek personelin kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        personelService.personelSil(id);
    }
}