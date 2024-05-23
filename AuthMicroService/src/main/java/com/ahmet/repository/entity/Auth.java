package com.ahmet.repository.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "tblauth")
@SuperBuilder  // PAY ATT !!!: 'SuperBuilder': Miras alma/verme olduğu için.
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Auth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(unique = true)
    String username;
    String email;
    String password;

}
