package com.kirac.randevusistemi.dto;

import java.time.LocalTime;

public class MusaitSaatDto {

    private LocalTime saat;
    private boolean musait;

    public MusaitSaatDto(
            LocalTime saat,
            boolean musait) {

        this.saat = saat;
        this.musait = musait;
    }

    public LocalTime getSaat() {
        return saat;
    }

    public void setSaat(LocalTime saat) {
        this.saat = saat;
    }

    public boolean isMusait() {
        return musait;
    }

    public void setMusait(boolean musait) {
        this.musait = musait;
    }
}