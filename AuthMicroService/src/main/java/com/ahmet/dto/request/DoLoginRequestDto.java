package com.ahmet.dto.request;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoLoginRequestDto {

    String username;
    String password;

}
