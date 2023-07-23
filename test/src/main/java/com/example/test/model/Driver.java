package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Drivers")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Driver {
    @Id
    @Column(name = "Driver_ID")
    private Integer driverId;

    @Column(name = "Driver_Name")
    private String driverName;
}
