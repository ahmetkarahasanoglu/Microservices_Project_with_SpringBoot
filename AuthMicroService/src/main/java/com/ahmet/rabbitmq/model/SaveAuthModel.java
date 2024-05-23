package com.ahmet.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ÖNEMLİ !!!!!!!!!!!!!
 * Mutlaka modeller serileştirilmelidir (implements Serializable).
 * Çünkü rabbitmq bu şekilde kullanıyor onu.
 * Ayrıca bu sınıfın aynısı iletilen (alıcı)[UserProfileMicroservice]
 * serviste de olmalıdır (package adına kadar aynı olmalı [consumer,
 * model, producer gibi]).
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveAuthModel implements Serializable {

    Long authid;
    String username;
    String email;

}
