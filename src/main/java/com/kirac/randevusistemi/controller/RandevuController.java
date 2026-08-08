package com.kirac.randevusistemi.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kirac.randevusistemi.dto.MusaitSaatDto;
import com.kirac.randevusistemi.entity.Randevu;
import com.kirac.randevusistemi.service.RandevuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Randevu İşlemleri",
        description = "Randevu oluşturma, listeleme, güncelleme, silme ve müsait saat sorgulama işlemleri"
)
@RestController
@RequestMapping("/api/randevular")
public class RandevuController {

    private final RandevuService randevuService;

    public RandevuController(
            RandevuService randevuService) {

        this.randevuService = randevuService;
    }

    @Operation(
            summary = "Yeni randevu oluşturur",
            description = "Yönetici müşteri seçerek, normal kullanıcı ise JWT hesabına bağlı müşteri kaydı üzerinden yeni randevu oluşturur."
    )
    @PostMapping
    public Randevu randevuEkle(
            @RequestBody Randevu randevu,
            Authentication authentication) {

        boolean yoneticiMi =
                authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                                authority.getAuthority()
                                        .equals("ROLE_YONETICI"));

        if (yoneticiMi) {
            return randevuService.randevuEkle(
                    randevu);
        }

        return randevuService
                .kullaniciRandevusuEkle(
                        randevu,
                        authentication.getName());
    }

    @Operation(
            summary = "Tüm randevuları listeler",
            description = "Sistemde kayıtlı bütün randevuları getirir."
    )
    @GetMapping
    public List<Randevu> tumRandevulariGetir() {

        return randevuService
                .tumRandevulariGetir();
    }

    @Operation(
            summary = "Giriş yapan kullanıcının randevularını getirir",
            description = "JWT ile giriş yapan kullanıcıya bağlı müşterinin randevularını tarih ve saate göre listeler."
    )
    @GetMapping("/benim")
    public List<Randevu> kullanicininRandevulariniGetir(
            Authentication authentication) {

        return randevuService
                .kullanicininRandevulariniGetir(
                        authentication.getName());
    }

    @Operation(
            summary = "ID ile randevu getirir",
            description = "Gönderilen randevu kimliğine ait kaydı getirir."
    )
    @GetMapping("/{id}")
    public Randevu idIleRandevuGetir(
            @Parameter(
                    description = "Getirilecek randevunun kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        return randevuService
                .idIleRandevuGetir(id);
    }

    @Operation(
            summary = "Randevu bilgilerini günceller",
            description = "Gönderilen ID değerine ait randevunun tarih, saat, personel, müşteri, hizmet ve durum bilgilerini günceller."
    )
    @PutMapping("/{id}")
    public Randevu randevuGuncelle(
            @Parameter(
                    description = "Güncellenecek randevunun kimliği",
                    example = "1"
            )
            @PathVariable Integer id,
            @RequestBody Randevu randevu) {

        return randevuService
                .randevuGuncelle(
                        id,
                        randevu);
    }

    @Operation(
            summary = "Kullanıcının kendi randevusunu iptal eder",
            description = "JWT ile giriş yapan kullanıcının yalnızca kendisine ait randevunun durumunu IPTAL_EDILDI olarak günceller."
    )
    @PutMapping("/benim/{id}/iptal")
    public Randevu kullaniciRandevusunuIptalEt(
            @Parameter(
                    description = "İptal edilecek randevunun kimliği",
                    example = "1"
            )
            @PathVariable Integer id,
            Authentication authentication) {

        return randevuService
                .kullaniciRandevusunuIptalEt(
                        id,
                        authentication.getName());
    }

    @Operation(
            summary = "Randevuyu siler",
            description = "Gönderilen ID değerine ait randevu kaydını sistemden siler."
    )
    @DeleteMapping("/{id}")
    public void randevuSil(
            @Parameter(
                    description = "Silinecek randevunun kimliği",
                    example = "1"
            )
            @PathVariable Integer id) {

        randevuService.randevuSil(id);
    }

    @Operation(
            summary = "Müsait randevu saatlerini listeler",
            description = "Seçilen personel, tarih ve hizmet süresine göre yalnızca müsait olan randevu başlangıç saatlerini getirir."
    )
    @GetMapping("/musait-saatler")
    public List<LocalTime> musaitSaatleriGetir(
            @Parameter(
                    description = "Müsaitliği sorgulanacak personelin kimliği",
                    example = "1"
            )
            @RequestParam Integer personelId,

            @Parameter(
                    description = "Müsait saatlerin sorgulanacağı tarih",
                    example = "2026-08-10"
            )
            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE)
            LocalDate tarih,

            @Parameter(
                    description = "Süresi dikkate alınacak hizmetin kimliği",
                    example = "1"
            )
            @RequestParam Integer hizmetId) {

        return randevuService
                .musaitSaatleriGetir(
                        personelId,
                        tarih,
                        hizmetId);
    }

    @Operation(
            summary = "Tüm saatlerin durumunu listeler",
            description = "Personelin çalışma saatlerini seçilen hizmet süresine göre müsait veya dolu bilgisiyle birlikte getirir."
    )
    @GetMapping("/saat-durumlari")
    public List<MusaitSaatDto> tumSaatDurumlariniGetir(
            @Parameter(
                    description = "Saat durumları sorgulanacak personelin kimliği",
                    example = "1"
            )
            @RequestParam Integer personelId,

            @Parameter(
                    description = "Saat durumlarının sorgulanacağı tarih",
                    example = "2026-08-10"
            )
            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE)
            LocalDate tarih,

            @Parameter(
                    description = "Süresi dikkate alınacak hizmetin kimliği",
                    example = "1"
            )
            @RequestParam Integer hizmetId) {

        return randevuService
                .tumSaatDurumlariniGetir(
                        personelId,
                        tarih,
                        hizmetId);
    }
}