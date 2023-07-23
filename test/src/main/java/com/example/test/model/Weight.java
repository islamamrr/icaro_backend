package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@Table(name = "Weights")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weight {

    @Id
    @Column(name = "Weight_ID")
    private Integer weightId;

    @Column(name = "FirstWeightCar")
    private Integer firstWeightCar;

    @Column(name = "SecondWeightCar")
    private String secondWeightCar;

    @Column(name = "NetWeight")
    private BigInteger netWeight;

    @Column(name = "Weight_Type")
    private String weightType;

    @Column(name = "CarTwoDate")
    private String carTwoDate;

    @Column(name = "CarOneTime")
    private String carOneTime;

    @Column(name = "CarTwoTime")
    private String carTwoTime;
}
