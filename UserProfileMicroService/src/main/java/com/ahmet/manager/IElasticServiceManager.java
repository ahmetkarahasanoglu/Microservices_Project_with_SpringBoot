package com.ahmet.manager;

import com.ahmet.repository.entity.UserProfile;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.ahmet.constants.EndPoints.SAVE;

@FeignClient(name = "elastic-sevice-manager", url = "http://localhost:9100/elastic/user", decode404 = true)
public interface IElasticServiceManager {

    @PostMapping(SAVE) // UserMikro'dan ElasticMikro'ya veri gönderimini sağlayacak.
    ResponseEntity<Void> addUser(@RequestBody UserProfile dto); // burda UserProfile ile AddUserRequestDto(karşı tarafta karşılayan yerde[ElasticMicroService/UserProfileController])'nun alanları aynı, sorun olmuyor.) ||| Bu metot birebir aynı olmalı ElasticMikro'nun UserProfileController'ındaki metotla.

}
