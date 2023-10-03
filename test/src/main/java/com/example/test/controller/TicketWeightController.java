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
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "siteNo", required = false) Integer siteNo,
            @RequestParam(value = "clientType", required = false) String clientType,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal totalNetWeight = ticketWeightService.getTotalNetWeightByItemNameAndDate(itemName, siteNo, clientType, startDate, endDate);
        return totalNetWeight;
    }
    @GetMapping("/output-rejected/weight")
    public BigDecimal getTotalOutputRejectedNetWeightSiteNoAndDate(
            @RequestParam(value = "clientType", required = false) String clientType,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal totalNetWeight = ticketWeightService.getTotalOutputRejectedNetWeightSiteNoAndDate(clientType, startDate, endDate);
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

    @GetMapping("/output/weight")
    public BigDecimal getTotalOutputNetWeightBySiteNoAndDate(
            @RequestParam(value = "siteNo", required = false) Integer siteNo,
            @RequestParam(value = "clientType", required = false) String clientType,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal totalNetWeight = ticketWeightService.getTotalOutputNetWeightBySiteNoAndDate(startDate, endDate, clientType, siteNo);
        return totalNetWeight;
    }

    //map of date: net weight for each item name filters: siteNo, center, village, clientType
    @GetMapping("/itemName-site/weight-date-list")
    public Map<String, BigDecimal> getNetWeightByDate(
            @RequestParam(value = "itemName", required = false) String itemName,
            @RequestParam(value = "siteNo", required = false) Integer siteNo,
            @RequestParam(value = "centerId", required = false) Integer centerId,
            @RequestParam(value = "villageId", required = false) Integer villageId,
            @RequestParam(value = "clientType", required = false) String clientType,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        Map<String, BigDecimal> netWeightMap = ticketWeightService.getDateNetWeightsMapByItemNameVillageCenter(itemName, siteNo, centerId, villageId, clientType, startDate, endDate);
        return netWeightMap;
    }

    //all tickets in a certain site on a certain day
    @GetMapping("/all")
    public List<TicketWeightDTO> getTicketsBySiteNoAndDate(
            @RequestParam("siteNo") Integer siteNo,
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//            @RequestParam("selectedDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate selectedDate
    ) {
        List<TicketWeight> tickets = ticketWeightService.getTicketsBySiteNoAndDate(siteNo, startDate, endDate);

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

    @RequestMapping(value = "/output/weight-list", method = RequestMethod.GET)
    public BigDecimal[] getOutputNetWeightsByDate(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal[] netWeightsByItemType = ticketWeightService.getOutputNetWeightsByDate( startDate, endDate);
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

    @GetMapping("/output-rejected/weight-list")
    public BigDecimal[] getOutputRejectedNetWeightsByDate(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
    ) {
        BigDecimal[] netWeightsByItemName = ticketWeightService.getOutputRejectedNetWeightsByDate(startDate, endDate);
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

    @GetMapping("/centers-net-weight-list")
    public ResponseEntity<Map<String, BigDecimal>> getCenterNetWeights(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate,
            @RequestParam("itemType") String itemType
    ) {
        Map<String, BigDecimal> centerNetWeights = ticketWeightService.getCenterNetWeights(startDate, endDate, itemType);
        return ResponseEntity.ok(centerNetWeights);
    }
    @GetMapping("/centers-accepted-net-weight-list")
    public ResponseEntity<Map<String, BigDecimal>> getCenterAcceptedNetWeights(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate,
            @RequestParam("itemName") String itemName
    ) {
        Map<String, BigDecimal> centerNetWeights = ticketWeightService.getCenterAcceptedNetWeights(startDate, endDate, itemName);
        return ResponseEntity.ok(centerNetWeights);
    }
}
