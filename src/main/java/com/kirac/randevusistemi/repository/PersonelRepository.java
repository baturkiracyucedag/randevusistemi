package com.kirac.randevusistemi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kirac.randevusistemi.entity.Personel;



public interface PersonelRepository
        extends JpaRepository<Personel, Integer> {

}