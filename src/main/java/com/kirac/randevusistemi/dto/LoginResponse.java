package com.kirac.randevusistemi.dto;

public class LoginResponse {

    private String token;
    private String tokenTuru;
    private String kullaniciAdi;
    private String rol;

    public LoginResponse(
            String token,
            String kullaniciAdi,
            String rol) {

        this.token = token;
        this.tokenTuru = "Bearer";
        this.kullaniciAdi = kullaniciAdi;
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public String getTokenTuru() {
        return tokenTuru;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public String getRol() {
        return rol;
    }
}