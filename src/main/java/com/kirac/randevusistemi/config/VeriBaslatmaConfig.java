package com.kirac.randevusistemi.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kirac.randevusistemi.entity.Kullanici;
import com.kirac.randevusistemi.entity.Rol;
import com.kirac.randevusistemi.repository.KullaniciRepository;
import com.kirac.randevusistemi.service.KullaniciService;

@Configuration
public class VeriBaslatmaConfig {

    @Bean
    public CommandLineRunner yoneticiOlustur(
            KullaniciRepository kullaniciRepository,
            KullaniciService kullaniciService) {

        return args -> {

            String yoneticiKullaniciAdi = "admin";

            if (!kullaniciRepository.existsByKullaniciAdi(
                    yoneticiKullaniciAdi)) {

                Kullanici yonetici = new Kullanici();

                yonetici.setKullaniciAdi(
                        yoneticiKullaniciAdi);

                yonetici.setEmail(
                        "admin@randevusistemi.com");

                yonetici.setSifre(
                        "123456");

                yonetici.setRol(
                        Rol.YONETICI);

                yonetici.setAktif(true);

                kullaniciService.kullaniciEkle(
                        yonetici);

                System.out.println(
                        "Varsayılan yönetici hesabı oluşturuldu.");
            }
        };
    }
}