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

import com.kirac.randevusistemi.entity.Hizmet;
import com.kirac.randevusistemi.service.HizmetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Hizmet İşlemleri",
        description = "Hizmet ekleme, listeleme, güncelleme, sorgulama ve silme işlemleri"
)
@RestController
@RequestMapping("/api/hizmetler")
public class HizmetController {

    private final HizmetService hizmetService;

    public HizmetController(
            HizmetService hizmetService) {

        this.hizmetService = hizmetService;
    }

    @Operation(
            summary = "Yeni hizmet oluşturur",
            description = "Hizmet adı, açıklaması, süresi, ücreti ve aktiflik bilgileriyle sisteme yeni bir hizmet ekler."
    )
    @PostMapping
    public Hizmet hizmetEkle(
            @RequestBody Hizmet hizmet) {

        return hizmetService.hizmetEkle(hizmet);
    }

    @Operation(
            summary = "Tüm hizmetleri listeler",
            description = "Sistemde kayıtlı olan bütün hizmetleri getirir."
    )
    @GetMapping
    public List<Hizmet> tumHizmetleriGetir() {

        return hizmetService.tumHizmetleriGetir();
    }

    @Operation(
            summary = "ID ile hizmet getirir",
            description = "Gönderilen hizmet kimliğine ait kayıtlı hizmet bilgilerini getirir."
    )
    @GetMapping("/{id}")
    public Hizmet idIleHizmetGetir(
            @Parameter(
                    description = "Bilgileri getirilecek hizmetin kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        return hizmetService.idIleHizmetGetir(id);
    }

    @Operation(
            summary = "Hizmet bilgilerini günceller",
            description = "Gönderilen kimliğe ait hizmetin adı, açıklaması, süresi, ücreti ve aktiflik bilgilerini günceller."
    )
    @PutMapping("/{id}")
    public Hizmet hizmetGuncelle(
            @Parameter(
                    description = "Bilgileri güncellenecek hizmetin kimliği",
                    example = "1"
            )
            @PathVariable Integer id,
            @RequestBody Hizmet hizmet) {

        return hizmetService.hizmetGuncelle(
                id,
                hizmet);
    }

    @Operation(
            summary = "Hizmeti siler",
            description = "Gönderilen kimliğe ait hizmet kaydını sistemden siler."
    )
    @DeleteMapping("/{id}")
    public void hizmetSil(
            @Parameter(
                    description = "Silinecek hizmetin kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        hizmetService.hizmetSil(id);
    }
}