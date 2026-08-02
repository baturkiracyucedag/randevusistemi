package com.kirac.randevusistemi.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String jwtSecret;
    private final long jwtExpiration;

    public JwtService(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration}") long jwtExpiration) {

        this.jwtSecret = jwtSecret;
        this.jwtExpiration = jwtExpiration;
    }

    public String tokenOlustur(
            String kullaniciAdi,
            String rol) {

        Date simdi = new Date();

        Date gecerlilikBitisi =
                new Date(
                        simdi.getTime()
                                + jwtExpiration);

        return Jwts.builder()
                .subject(kullaniciAdi)
                .claim("rol", rol)
                .issuedAt(simdi)
                .expiration(gecerlilikBitisi)
                .signWith(imzalamaAnahtariniGetir())
                .compact();
    }

    public String kullaniciAdiniGetir(
            String token) {

        return tumClaimleriGetir(token)
                .getSubject();
    }

    public String rolBilgisiniGetir(
            String token) {

        return tumClaimleriGetir(token)
                .get("rol", String.class);
    }

    public boolean tokenGecerliMi(
            String token,
            UserDetails userDetails) {

        String kullaniciAdi =
                kullaniciAdiniGetir(token);

        return kullaniciAdi.equals(
                userDetails.getUsername())
                && !tokenSuresiDolmusMu(token);
    }

    private boolean tokenSuresiDolmusMu(
            String token) {

        Date bitisTarihi =
                tumClaimleriGetir(token)
                        .getExpiration();

        return bitisTarihi.before(
                new Date());
    }

    private Claims tumClaimleriGetir(
            String token) {

        return Jwts.parser()
                .verifyWith(
                        imzalamaAnahtariniGetir())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey imzalamaAnahtariniGetir() {

        byte[] anahtarBaytlari =
                Decoders.BASE64.decode(
                        jwtSecret);

        return Keys.hmacShaKeyFor(
                anahtarBaytlari);
    }
}