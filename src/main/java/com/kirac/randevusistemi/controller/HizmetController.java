package com.kirac.randevusistemi.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirac.randevusistemi.entity.Hizmet;
import com.kirac.randevusistemi.service.HizmetService;

@RestController
@RequestMapping("/api/hizmetler")
public class HizmetController {

    private final HizmetService hizmetService;

    public HizmetController(HizmetService hizmetService) {
        this.hizmetService = hizmetService;
    }

    @PostMapping
    public Hizmet hizmetEkle(@RequestBody Hizmet hizmet) {
        return hizmetService.hizmetEkle(hizmet);
    }

    @GetMapping
    public List<Hizmet> tumHizmetleriGetir() {
        return hizmetService.tumHizmetleriGetir();
    }

    @GetMapping("/{id}")
    public Hizmet idIleHizmetGetir(@PathVariable Integer id) {
        return hizmetService.idIleHizmetGetir(id);
    }
}