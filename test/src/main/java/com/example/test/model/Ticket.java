//package com.example.test.model;
//
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Table(name = "Tickets")
//@Entity
//@Builder
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
////@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
////@JsonInclude(JsonInclude.Include.NON_EMPTY)
//public class Ticket {
//
//    @Id
//    @Column(name = "Ticket_ID")
//    private Integer ticketId;
//
//    @Column(name = "Ticket_ID_Order")
//    private Integer ticketIdOrder;
//
//    @Column(name = "vehicle_Number")
//    private String vehicleNumber;
//
//    @Column(name = "Notes")
//    private String notes;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Item_ID")
//    private Item item;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Weight_ID")
//    private Weight weight;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Center_ID")
//    private Center center;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Village_ID")
//    private Village village;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Driver_ID")
//    private Driver driver;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "Client_ID")
//    private Client client;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "City_ID")
//    private City city;
//
//    @Column(name = "EnterMethod")
//    private String enterMethod;
//
////    @Column(name = "Site_Name")
////    private String siteNo;
//
//    @Column(name = "Site_ID")
//    private Integer siteNo;
//}
