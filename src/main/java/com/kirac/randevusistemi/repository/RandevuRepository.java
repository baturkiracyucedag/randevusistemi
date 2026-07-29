package com.kirac.randevusistemi.repository;

import java.util.List;
import java.time.LocalDate;


import org.springframework.data.jpa.repository.JpaRepository;

import com.kirac.randevusistemi.entity.Randevu;

public interface RandevuRepository
                extends JpaRepository<Randevu, Integer> {

       
        List<Randevu> findByPersonelIdAndTarih(
                        Integer personelId,
                        LocalDate tarih);

}
