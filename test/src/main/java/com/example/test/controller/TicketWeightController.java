package com.example.test.controller;

import com.example.test.dto.TicketWeightDTO;
import com.example.test.dto.TicketWeightUpdateRequest;
import com.example.test.model.TicketWeight;
import com.example.test.service.TicketWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.test.dto.TicketWeightDTO.toDto;

@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com"}, allowCredentials = "true")
@RequestMapping("/tickets")
public class TicketWeightController {

    @Autowired
    private TicketWeightService ticketWeightService;

    //total net weight by item name w/wo siteNo
    @GetMapping("/itemName/weight")
    public BigDecimal getTotalNetWeightByItemNameSiteNoAndDate(
            @RequestParam("itemName") String itemName,
            @RequestParam(value = "siteNo", required = false) Integer siteNo,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal totalNetWeight = ticketWeightService.getTotalNetWeightByItemNameAndDate(itemName, siteNo, startDate, endDate);
        return totalNetWeight;
    }

    //total net weight by item type w/wo siteNo
    @GetMapping("/itemType/weight")
    public BigDecimal getTotalNetWeightByItemTypeSiteNoAndDate(
            @RequestParam("itemType") String itemType,
            @RequestParam(value = "siteNo", required = false) Integer siteNo,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal totalNetWeight = ticketWeightService.getTotalNetWeightByItemTypeAndDate(itemType, siteNo, startDate, endDate);
        return totalNetWeight;
    }

    //map of date: net weight for each item name filters: siteNo, center, village
    @GetMapping("/itemName-site/weight-date-list")
    public Map<String, BigDecimal> getNetWeightByDate(
            @RequestParam("itemName") String itemName,
            @RequestParam(value = "siteNo", required = false) Integer siteNo,
            @RequestParam(value = "centerId", required = false) Integer centerId,
            @RequestParam(value = "villageId", required = false) Integer villageId,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        Map<String, BigDecimal> netWeightMap = ticketWeightService.getDateNetWeightsMapByItemNameVillageCenter(itemName, siteNo, centerId, villageId, startDate, endDate);
        return netWeightMap;
    }

    //all tickets in a certain site on a certain day
    @GetMapping("/all")
    public List<TicketWeightDTO> getTicketsBySiteNoAndDate(
            @RequestParam("siteNo") Integer siteNo,
            @RequestParam("selectedDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate selectedDate
    ) {
        List<TicketWeight> tickets = ticketWeightService.getTicketsBySiteNoAndDate(siteNo, selectedDate);

        List<TicketWeightDTO> ticketWeightDTOS = new ArrayList<>();

        for (TicketWeight ticket : tickets) {
            TicketWeightDTO ticketDto = toDto(ticket);
            ticketWeightDTOS.add(ticketDto);
        }
        return ticketWeightDTOS;
    }

    @GetMapping("/itemType/weight-list")
    public BigDecimal[] getNetWeightsByItemTypeAndDate(
            @RequestParam(value = "itemType") String itemType,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal[] netWeightsByItemType = ticketWeightService.getNetWeightsByItemTypeAndDate(itemType, startDate, endDate);
        return netWeightsByItemType;
    }

    @GetMapping("/itemName/weight-list")
    public BigDecimal[] getNetWeightsByItemNameAndDate(
            @RequestParam(value = "itemName") String itemName,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal[] netWeightsByItemName = ticketWeightService.getNetWeightsByItemNameAndDate(itemName, startDate, endDate);
        return netWeightsByItemName;
    }

    @PutMapping
    public ResponseEntity<TicketWeight> createTicketWeight(@RequestBody TicketWeight ticketWeight) {
        TicketWeight createdTicket = ticketWeightService.createTicketWeight(ticketWeight);
        return ResponseEntity.ok(createdTicket);
    }



    @PutMapping("/{ticketId}/{siteNo}")
    public ResponseEntity<TicketWeight> updateTicket(
            @PathVariable Integer ticketId,
            @PathVariable Integer siteNo,
            @RequestBody TicketWeightUpdateRequest updateRequest
    ) {
        TicketWeight updatedTicket = ticketWeightService.updateTicket(ticketId, siteNo, updateRequest);
        return ResponseEntity.ok(updatedTicket);
    }
}