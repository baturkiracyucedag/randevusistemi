package com.kirac.randevusistemi.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirac.randevusistemi.entity.Personel;
import com.kirac.randevusistemi.service.PersonelService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/personeller")
public class PersonelController {

    private final PersonelService personelService;

    public PersonelController(PersonelService personelService) {
        this.personelService = personelService;
    }

    @PostMapping
    public Personel personelEkle(@RequestBody Personel personel) {
        return personelService.personelEkle(personel);
    }

    @GetMapping
    public List<Personel> tumPersonelleriGetir() {
        return personelService.tumPersonelleriGetir();
    }

    @GetMapping("/{id}")
    public Personel idIlePersonelGetir(@PathVariable Integer id) {
        return personelService.idIlePersonelGetir(id);
    }
    @PutMapping("/{id}")
    public Personel personelGuncelle(@PathVariable Integer id,
        @RequestBody Personel personel) {

         return personelService.personelGuncelle(id, personel);
    }
    @DeleteMapping("/{id}")
     public void personelSil(@PathVariable Integer id) {
    personelService.personelSil(id);
     }
}