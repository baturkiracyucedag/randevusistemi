package com.kirac.randevusistemi.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kirac.randevusistemi.entity.Kullanici;
import com.kirac.randevusistemi.entity.Rol;
import com.kirac.randevusistemi.repository.KullaniciRepository;

@Service
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;

    public KullaniciService(
            KullaniciRepository kullaniciRepository,
            PasswordEncoder passwordEncoder) {

        this.kullaniciRepository = kullaniciRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Kullanici kullaniciEkle(Kullanici kullanici) {

        if (kullanici.getKullaniciAdi() == null
                || kullanici.getKullaniciAdi().isBlank()) {

            throw new IllegalArgumentException(
                    "Kullanıcı adı boş bırakılamaz.");
        }

        if (kullanici.getEmail() == null
                || kullanici.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "E-posta adresi boş bırakılamaz.");
        }

        if (kullanici.getSifre() == null
                || kullanici.getSifre().isBlank()) {

            throw new IllegalArgumentException(
                    "Şifre boş bırakılamaz.");
        }

        if (kullaniciRepository.existsByKullaniciAdi(
                kullanici.getKullaniciAdi())) {

            throw new IllegalArgumentException(
                    "Bu kullanıcı adı zaten kullanılmaktadır.");
        }

        if (kullaniciRepository.existsByEmail(
                kullanici.getEmail())) {

            throw new IllegalArgumentException(
                    "Bu e-posta adresi zaten kullanılmaktadır.");
        }

        if (kullanici.getRol() == null) {
            kullanici.setRol(Rol.KULLANICI);
        }

        if (kullanici.getAktif() == null) {
            kullanici.setAktif(true);
        }

        kullanici.setSifre(
                passwordEncoder.encode(
                        kullanici.getSifre()));

        return kullaniciRepository.save(kullanici);
    }

    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    public Kullanici idIleKullaniciGetir(Integer id) {

        return kullaniciRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Kullanıcı bulunamadı."));
    }

    public Kullanici kullaniciGuncelle(
            Integer id,
            Kullanici yeniKullanici) {

        Kullanici mevcutKullanici =
                kullaniciRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Kullanıcı bulunamadı."));

        if (yeniKullanici.getKullaniciAdi() == null
                || yeniKullanici.getKullaniciAdi().isBlank()) {

            throw new IllegalArgumentException(
                    "Kullanıcı adı boş bırakılamaz.");
        }

        if (yeniKullanici.getEmail() == null
                || yeniKullanici.getEmail().isBlank()) {

            throw new IllegalArgumentException(
                    "E-posta adresi boş bırakılamaz.");
        }

        if (yeniKullanici.getSifre() == null
                || yeniKullanici.getSifre().isBlank()) {

            throw new IllegalArgumentException(
                    "Şifre boş bırakılamaz.");
        }

        kullaniciRepository
                .findByKullaniciAdi(
                        yeniKullanici.getKullaniciAdi())
                .filter(kullanici ->
                        !kullanici.getId().equals(id))
                .ifPresent(kullanici -> {
                    throw new IllegalArgumentException(
                            "Bu kullanıcı adı zaten kullanılmaktadır.");
                });

        kullaniciRepository
                .findByEmail(
                        yeniKullanici.getEmail())
                .filter(kullanici ->
                        !kullanici.getId().equals(id))
                .ifPresent(kullanici -> {
                    throw new IllegalArgumentException(
                            "Bu e-posta adresi zaten kullanılmaktadır.");
                });

        mevcutKullanici.setKullaniciAdi(
                yeniKullanici.getKullaniciAdi());

        mevcutKullanici.setEmail(
                yeniKullanici.getEmail());

        mevcutKullanici.setSifre(
                passwordEncoder.encode(
                        yeniKullanici.getSifre()));

        if (yeniKullanici.getRol() == null) {
            mevcutKullanici.setRol(Rol.KULLANICI);
        } else {
            mevcutKullanici.setRol(
                    yeniKullanici.getRol());
        }

        if (yeniKullanici.getAktif() == null) {
            mevcutKullanici.setAktif(true);
        } else {
            mevcutKullanici.setAktif(
                    yeniKullanici.getAktif());
        }

        return kullaniciRepository.save(
                mevcutKullanici);
    }

    public void kullaniciSil(Integer id) {

        Kullanici kullanici =
                kullaniciRepository.findById(id)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Kullanıcı bulunamadı."));

        kullaniciRepository.delete(kullanici);
    }
}