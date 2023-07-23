//package com.example.test.dto;
//
//import com.example.test.model.Ticket;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigInteger;
//
//@Builder
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class TicketDto {
//    private Integer ticketId;
//    private Integer ticketIdOrder;
//    private String weightType;
//    private String itemName;
////    private String itemType;
//    private String clientName;
//    private String cityName;
//    private String driverName;
//    private String vehicleNumber;
//    private Integer firstWeightCar;
//    private String secondWeightCar;
//    private BigInteger netWeight;
//    private String carTwoDate;
//    private String carOneTime;
//    private String carTwoTime;
//    private String enterMethod;
//    private Integer siteNo;
//
//    public static TicketDto toDto(Ticket entity) {
//        return TicketDto.builder()
//                .ticketId(entity.getTicketId() == null ? null : entity.getTicketId())
//                .ticketIdOrder(entity.getTicketIdOrder() == null ? null : entity.getTicketIdOrder())
//                .weightType(entity.getWeight().getWeightType() == null ? null : entity.getWeight().getWeightType())
//                .itemName(entity.getItem().getItemName() == null ? null : entity.getItem().getItemName())
////                .itemType(entity.getItem().getItemType() == null ? null : entity.getItem().getItemType())
//                .clientName(entity.getClient().getClientName() == null ? null : entity.getClient().getClientName())
//                .cityName(entity.getCity().getCityName() == null ? null : entity.getCity().getCityName())
//                .driverName(entity.getDriver().getDriverName() == null ? null : entity.getDriver().getDriverName())
//                .vehicleNumber(entity.getVehicleNumber() == null ? null : entity.getVehicleNumber())
//                .firstWeightCar(entity.getWeight().getFirstWeightCar() == null ? null : entity.getWeight().getFirstWeightCar())
//                .secondWeightCar(entity.getWeight().getSecondWeightCar() == null ? null : entity.getWeight().getSecondWeightCar())
//                .netWeight(entity.getWeight().getNetWeight() == null ? null : entity.getWeight().getNetWeight())
//                .carTwoDate(entity.getWeight().getCarTwoDate() == null ? null : entity.getWeight().getCarTwoDate())
//                .carOneTime(entity.getWeight().getCarOneTime() == null ? null : entity.getWeight().getCarOneTime())
//                .carTwoTime(entity.getWeight().getCarTwoTime() == null ? null : entity.getWeight().getCarTwoTime())
//                .enterMethod(entity.getEnterMethod() == null ? null : entity.getEnterMethod())
//                .siteNo(entity.getSiteNo() == null ? null : entity.getSiteNo())
//                .build();
//    }
//}
