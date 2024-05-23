package com.ahmet.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder  // PAY ATT !!!: 'SuperBuilder': Miras alma/verme olduğu için.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    Long createat;
    Long updateat;
    boolean state;

}
