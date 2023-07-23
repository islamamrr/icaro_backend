package com.example.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TicketWeightUpdateRequest {
    private String carTwoDate;
    private Integer netWeight;
    private String carTwoTime;
    private Integer secondWeight;
}
