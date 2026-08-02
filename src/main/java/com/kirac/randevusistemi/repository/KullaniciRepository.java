package com.kirac.randevusistemi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirac.randevusistemi.entity.Kullanici;

public interface KullaniciRepository
        extends JpaRepository<Kullanici, Integer> {

    Optional<Kullanici> findByKullaniciAdi(
            String kullaniciAdi);

    Optional<Kullanici> findByEmail(
            String email);

    boolean existsByKullaniciAdi(
            String kullaniciAdi);

    boolean existsByEmail(
            String email);
}