package com.kirac.randevusistemi.controller;
import com.kirac.randevusistemi.dto.RegisterRequest;
import com.kirac.randevusistemi.entity.Kullanici;
import com.kirac.randevusistemi.entity.Musteri;
import com.kirac.randevusistemi.entity.Rol;
import com.kirac.randevusistemi.service.KullaniciService;
import com.kirac.randevusistemi.service.MusteriService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kirac.randevusistemi.dto.LoginRequest;
import com.kirac.randevusistemi.dto.LoginResponse;
import com.kirac.randevusistemi.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth")
@Tag(
        name = "Kimlik Doğrulama",
        description = "Kullanıcı giriş işlemleri"
)
public class AuthController {
        private final KullaniciService kullaniciService;
private final MusteriService musteriService;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

  public AuthController(
        AuthenticationManager authenticationManager,
        JwtService jwtService,
        KullaniciService kullaniciService,
        MusteriService musteriService) {

    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
    this.kullaniciService = kullaniciService;
    this.musteriService = musteriService;
}

    @Operation(
            summary = "Kullanıcı girişi",
            description = "Kullanıcı adı ve şifre ile giriş yaparak JWT Token üretir."
    )
    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getKullaniciAdi(),
                                request.getSifre()));

        String rol =
                authentication.getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        .replace("ROLE_", "");

        String token =
                jwtService.tokenOlustur(
                        request.getKullaniciAdi(),
                        rol);

        return new LoginResponse(
                token,
                request.getKullaniciAdi(),
                rol);
    }
    @Operation(
        summary = "Yeni kullanıcı kaydı oluşturur",
        description = "Yeni müşteri ve ona bağlı normal kullanıcı hesabı oluşturur."
)
@PostMapping("/register")
public Kullanici register(
        @RequestBody RegisterRequest request) {

    Musteri musteri = new Musteri();

    musteri.setAd(request.getAd());
    musteri.setSoyad(request.getSoyad());
    musteri.setTelefon(request.getTelefon());
    musteri.setEmail(request.getEmail());
    musteri.setAktif(true);

    Musteri kaydedilenMusteri =
            musteriService.musteriEkle(musteri);

    Kullanici kullanici = new Kullanici();

    kullanici.setKullaniciAdi(
            request.getKullaniciAdi());

    kullanici.setEmail(request.getEmail());
    kullanici.setSifre(request.getSifre());
    kullanici.setRol(Rol.KULLANICI);
    kullanici.setAktif(true);
    kullanici.setMusteri(kaydedilenMusteri);

    return kullaniciService.kullaniciEkle(kullanici);
}
    
}