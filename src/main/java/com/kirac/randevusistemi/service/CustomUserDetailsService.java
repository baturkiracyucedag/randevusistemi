package com.kirac.randevusistemi.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kirac.randevusistemi.entity.Kullanici;
import com.kirac.randevusistemi.repository.KullaniciRepository;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final KullaniciRepository kullaniciRepository;

    public CustomUserDetailsService(
            KullaniciRepository kullaniciRepository) {

        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    public UserDetails loadUserByUsername(
            String kullaniciAdi)
            throws UsernameNotFoundException {

        Kullanici kullanici =
                kullaniciRepository
                        .findByKullaniciAdi(
                                kullaniciAdi)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Kullanıcı bulunamadı."));

        return User.builder()
                .username(
                        kullanici.getKullaniciAdi())
                .password(
                        kullanici.getSifre())
                .roles(
                        kullanici.getRol().name())
                .disabled(
                        !Boolean.TRUE.equals(
                                kullanici.getAktif()))
                .build();
    }
}