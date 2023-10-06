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
    private String clientName;
    private String clientType;
    private String villageName;
    private String itemType;
    private String itemName;
    private String vehicleNumber;
    private Integer firstWeight;
    private Integer secondWeight;
    private Integer netWeight;
    private String carTwoDate;
    private String carOneTime;
    private String carTwoTime;
    private String driverName;
    private String enterMethod;

    public static TicketWeightDTO toDto(TicketWeight entity) {
        return TicketWeightDTO.builder()
                .ticketId(entity.getId().getTicketId() == null ? null : entity.getId().getTicketId())
                .clientName(entity.getClientName() == null ? null : entity.getClientName())
                .clientType(entity.getClientType() == null ? null : entity.getClientType())
                .villageName(entity.getVillage() == null ? null : entity.getVillage().getVillageName())
                .itemType(entity.getItemType() == null ? null : entity.getItemType())
                .itemName(entity.getItemName() == null ? null : entity.getItemName())
                .vehicleNumber(entity.getVehicleNumber() == null ? null : entity.getVehicleNumber())
                .firstWeight(entity.getFirstWeight() == null ? null : entity.getFirstWeight())
                .secondWeight(entity.getSecondWeight() == null ? null : entity.getSecondWeight())
                .netWeight(entity.getNetWeight() == null ? null : entity.getNetWeight())
                .carTwoDate(entity.getCarTwoDate() == null ? null : entity.getCarTwoDate())
                .carOneTime(entity.getCarOneTime() == null ? null : entity.getCarOneTime())
                .carTwoTime(entity.getCarTwoTime() == null ? null : entity.getCarTwoTime())
                .enterMethod(entity.getEnterMethod() == null ? null : entity.getEnterMethod())
                .driverName(entity.getDriverName() == null ? null : entity.getDriverName())
//                .carOneDate(entity.getCarOneDate() == null ? null : entity.getCarOneDate())
//                .centerName(entity.getCenter().getCenterName() == null ? null : entity.getCenter().getCenterName())
                .build();
    }
}
