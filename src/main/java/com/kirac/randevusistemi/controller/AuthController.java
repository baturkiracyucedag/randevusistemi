package com.kirac.randevusistemi.controller;

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

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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
}