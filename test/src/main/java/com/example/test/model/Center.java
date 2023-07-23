package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Center")
public class Center {
    @Id
    @Column(name = "Center_ID")
    private Integer centerId;

    @Column(name = "Center_Name")
    private String centerName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "City_ID")
    private City city;
}
