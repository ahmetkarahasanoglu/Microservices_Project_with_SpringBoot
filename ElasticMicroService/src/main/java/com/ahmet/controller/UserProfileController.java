package com.ahmet.controller;

import com.ahmet.dto.request.AddUserRequestDto;
import com.ahmet.dto.request.BaseRequestDto;
import com.ahmet.repository.entity.UserProfile;
import com.ahmet.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.ahmet.constants.EndPoints.*;

/**
 * Elastic search, verileri RAM'da tutar. (hızlı çalışır).
 *
 * ElasticMicroService'inin doğru çalışma şekli şöyle olmalıdır:
 * diğer mikroservisler ElasticMicroService'i günceller.
 * Sorgularımız da ElasticMicroService üzerinden yapılmalıdır
 * (hızlı olması için).
 */
@RestController
@RequestMapping(ELASTIC+USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(SAVE) // UserProfileMicroService'inden gönderilen veriyi burda karşılıyoruz.
    public ResponseEntity<Void> addUser(@RequestBody AddUserRequestDto dto) {
        userProfileService.saveDto(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping(GETALL)
    public ResponseEntity<Iterable<UserProfile>> findAll() {
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping(GETALL_VIP)
    @PreAuthorize("hasAuthority('VIP') or hasAuthority('OzerlMusteri')") // 'Vip' veya 'OzelMusteri' yetkisine sahipse girebilir.
    public ResponseEntity<Iterable<UserProfile>> findAllVip() {
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @GetMapping(GETALL_BEN)
    @PreAuthorize("hasAuthority('BEN')") // 'BEN' yetkisi sistemde tanımladığımız bir yetki değil, bu url ile giriş yapılamaz. "Access is denied" yazar.
    public ResponseEntity<Iterable<UserProfile>> findAllBen() {
        return ResponseEntity.ok(userProfileService.findAll());
    }

    @PostMapping(GETALLPAGE) // (with pagination features)
    public ResponseEntity<Page<UserProfile>> findAll(@RequestBody BaseRequestDto dto) {
        return ResponseEntity.ok(userProfileService.findAll(dto));
    }

}
