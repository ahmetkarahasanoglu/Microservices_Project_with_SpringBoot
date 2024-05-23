package com.ahmet.manager;

import com.ahmet.dto.request.UserProfileSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.ahmet.constants.EndPoints.SAVE;

/**
 * @FeignClient: diğer mikroservislere http request atmak için kullanılır.
 * Auth Mikroservis ile UserProfile Mikroservis arasında iletişim kurmak
 * istiyoruz; Auth'da register yapan bir dto'yu UserProfile'da da  kayıt
 * etmek istiyoruz.
 * Her FeignClient için benzersiz isim vermelisiniz - @FeignClient(name="....")
 * Burada kullanılan url, istek atılacak olan sınıfın requestmapping
 * adresi olmalı.
 */
@FeignClient(name = "user-profile-manager",
            url = "http://localhost:9093/user", // UserProfileController'daki "/user"
            decode404 = true)
public interface IUserProfileManager {

    @PostMapping(SAVE)
    ResponseEntity<Boolean> save(@RequestBody UserProfileSaveRequestDto dto); // UserProfileMicroService / UserProfileController'daki metodu yazdık gövdesiz olarak.

}
