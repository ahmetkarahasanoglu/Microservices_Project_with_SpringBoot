package com.ahmet.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import javax.persistence.*;

@Entity
@Table(name = "tbluserprofile")
@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserProfile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long authid;
    String username;
    String email;
    String ad;
    String adres;
    String telefon;
    String avatar;

}
