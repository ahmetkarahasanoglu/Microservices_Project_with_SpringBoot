package com.ahmet.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@SuperBuilder  // PAY ATT !!!: 'SuperBuilder': Miras alma/verme olduğu için.
@MappedSuperclass  // miras veren sınıf
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    Long createat;
    Long updateat;
    boolean state;

}
