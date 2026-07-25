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

@RestController
@RequestMapping("/api/musteriler")
public class MusteriController {

    private final MusteriService musteriService;

    public MusteriController(MusteriService musteriService) {
        this.musteriService = musteriService;
    }

    @PostMapping
    public Musteri musteriEkle(@RequestBody Musteri musteri) {
        return musteriService.musteriEkle(musteri);
    }

    @GetMapping
    public List<Musteri> tumMusterileriGetir() {
        return musteriService.tumMusterileriGetir();
    }

    @GetMapping("/{id}")
    public Musteri idIleMusteriGetir(@PathVariable Integer id) {
        return musteriService.idIleMusteriGetir(id);
    }

    @PutMapping("/{id}")
    public Musteri musteriGuncelle(
            @PathVariable Integer id,
            @RequestBody Musteri musteri) {

        return musteriService.musteriGuncelle(id, musteri);
    }

    @DeleteMapping("/{id}")
    public void musteriSil(@PathVariable Integer id) {
        musteriService.musteriSil(id);
    }
}