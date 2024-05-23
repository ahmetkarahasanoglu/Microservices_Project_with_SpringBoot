package com.ahmet.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Biz verilerimizi Eleasticsearch repository'sinde de tutmamız
 * gerekiyo.
 */

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(indexName = "userprofile") // bu tablonun adı olmuş oluyo "userprofile"
public class UserProfile extends BaseEntity {

    @Id
    String id; // uuid  |||  Bu alanı biz yeni ekledik. ElasticMicroService'in kendisinin oluşturacağı özel bir id'dir.
    Long userprofileid; // 'id' idi, 'userprofileid' yaptık. || Bu ve aşağıdaki alanları UserProfileMicroService'in repository'sindeki UserProfile sınıfından kopyalayıp aldık.
    Long authid;
    String username;
    String email;
    String ad;
    String adres;
    String telefon;
    String avatar;

}
