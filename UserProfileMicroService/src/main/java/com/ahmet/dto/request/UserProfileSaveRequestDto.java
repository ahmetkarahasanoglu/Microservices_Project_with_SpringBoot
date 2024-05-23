package com.ahmet.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileSaveRequestDto {

    Long authid;
    String username;
    String email;

}
