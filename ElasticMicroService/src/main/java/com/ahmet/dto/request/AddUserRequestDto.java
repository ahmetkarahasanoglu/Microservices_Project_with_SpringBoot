package com.ahmet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddUserRequestDto {

    Long id; // bu alanları UserProfileMicroService'deki UserProfile entity'sinden kopyalayıp aldık.
    Long authid;
    String username;
    String email;
    String ad;
    String adres;
    String telefon;
    String avatar;

}
