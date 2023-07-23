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
@Table(name = "Tickets_Weights")
public class TicketWeight {

    @EmbeddedId
    private CompositeKey id;

    @Column(name = "Client_Name")
    private String clientName;

    @Column(name = "Client_Type")
    private String clientType;

    @Column(name = "Driver_Name")
    private String driverName;

    @Column(name = "Item_Name")
    private String itemName;

    @Column(name = "Item_Type")
    private String itemType;

    @Column(name = "Vehicle_Number")
    private String vehicleNumber;

    @Column(name = "Notes")
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Center_ID")
    private Center center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Village_ID")
    private Village village;

    @Column(name = "FW")
    private Integer firstWeight;

    @Column(name = "SW")
    private Integer secondWeight;

    @Column(name = "NetW")
    private Integer netWeight;

    @Column(name = "FW_Date")
    private String carOneDate;

    @Column(name = "FW_Time")
    private String carOneTime;

    @Column(name = "SW_Date")
    private String carTwoDate;

    @Column(name = "SW_Time")
    private String carTwoTime;

    @Column(name = "EnterMethod")
    private String enterMethod;
}
