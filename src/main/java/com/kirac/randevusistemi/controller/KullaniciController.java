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

import com.kirac.randevusistemi.entity.Kullanici;
import com.kirac.randevusistemi.service.KullaniciService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Kullanıcı İşlemleri",
        description = "Kullanıcı ekleme, listeleme, güncelleme, sorgulama ve silme işlemleri"
)
@RestController
@RequestMapping("/api/kullanicilar")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    public KullaniciController(
            KullaniciService kullaniciService) {

        this.kullaniciService = kullaniciService;
    }

    @Operation(
            summary = "Yeni kullanıcı oluşturur",
            description = "Kullanıcı adı, e-posta, şifre, rol ve aktiflik bilgileriyle sisteme yeni kullanıcı ekler."
    )
    @PostMapping
    public Kullanici kullaniciEkle(
            @RequestBody Kullanici kullanici) {

        return kullaniciService.kullaniciEkle(kullanici);
    }

    @Operation(
            summary = "Tüm kullanıcıları listeler",
            description = "Sistemde kayıtlı bütün kullanıcıları getirir."
    )
    @GetMapping
    public List<Kullanici> tumKullanicilariGetir() {

        return kullaniciService.tumKullanicilariGetir();
    }

    @Operation(
            summary = "ID ile kullanıcı getirir",
            description = "Gönderilen kullanıcı kimliğine ait kayıtlı kullanıcı bilgilerini getirir."
    )
    @GetMapping("/{id}")
    public Kullanici idIleKullaniciGetir(
            @Parameter(
                    description = "Bilgileri getirilecek kullanıcının kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        return kullaniciService.idIleKullaniciGetir(id);
    }

    @Operation(
            summary = "Kullanıcı bilgilerini günceller",
            description = "Gönderilen kimliğe ait kullanıcının kullanıcı adı, e-posta, şifre, rol ve aktiflik bilgilerini günceller."
    )
    @PutMapping("/{id}")
    public Kullanici kullaniciGuncelle(
            @Parameter(
                    description = "Bilgileri güncellenecek kullanıcının kimliği",
                    example = "1"
            )
            @PathVariable Integer id,
            @RequestBody Kullanici kullanici) {

        return kullaniciService.kullaniciGuncelle(
                id,
                kullanici);
    }

    @Operation(
            summary = "Kullanıcıyı siler",
            description = "Gönderilen kimliğe ait kullanıcı kaydını sistemden siler."
    )
    @DeleteMapping("/{id}")
    public void kullaniciSil(
            @Parameter(
                    description = "Silinecek kullanıcının kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        kullaniciService.kullaniciSil(id);
    }
}