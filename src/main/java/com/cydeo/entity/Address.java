package com.cydeo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="addresses")
public class Address extends BaseEntity{

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zipCode;
}
