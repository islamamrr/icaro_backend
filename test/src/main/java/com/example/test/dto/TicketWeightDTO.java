package com.example.test.dto;

import com.example.test.model.TicketWeight;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketWeightDTO {
    private Integer ticketId;
    private String itemType;
    private String itemName;
    //    private String centerName;
//    private String villageName;
    private String clientName;
    private String driverName;
    private String vehicleNumber;
    private Integer firstWeight;
    private Integer secondWeight;
    private Integer netWeight;
    //    private String carOneDate;
    private String carTwoDate;
    private String carOneTime;
    private String carTwoTime;
    private String enterMethod;
    private Integer siteNo;

    public static TicketWeightDTO toDto(TicketWeight entity) {
        return TicketWeightDTO.builder()
                .ticketId(entity.getId().getTicketId() == null ? null : entity.getId().getTicketId())
                .itemType(entity.getItemType() == null ? null : entity.getItemType())
                .itemName(entity.getItemName() == null ? null : entity.getItemName())
                .clientName(entity.getClientName() == null ? null : entity.getClientName())
                .driverName(entity.getDriverName() == null ? null : entity.getDriverName())
                .vehicleNumber(entity.getVehicleNumber() == null ? null : entity.getVehicleNumber())
//                .centerName(entity.getCenter().getCenterName() == null ? null : entity.getCenter().getCenterName())
//                .villageName(entity.getVillage().getVillageName() == null ? null : entity.getVillage().getVillageName())
                .firstWeight(entity.getFirstWeight() == null ? null : entity.getFirstWeight())
                .secondWeight(entity.getSecondWeight() == null ? null : entity.getSecondWeight())
                .netWeight(entity.getNetWeight() == null ? null : entity.getNetWeight())
//                .carOneDate(entity.getCarOneDate() == null ? null : entity.getCarOneDate())
                .carTwoDate(entity.getCarTwoDate() == null ? null : entity.getCarTwoDate())
                .carOneTime(entity.getCarOneTime() == null ? null : entity.getCarOneTime())
                .carTwoTime(entity.getCarTwoTime() == null ? null : entity.getCarTwoTime())
                .enterMethod(entity.getEnterMethod() == null ? null : entity.getEnterMethod())
                .siteNo(entity.getId().getSiteNo() == null ? null : entity.getId().getSiteNo())
                .build();
    }
}
