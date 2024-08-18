//package com.example.test.controller;
//
//import com.example.test.dto.TicketDto;
//import com.example.test.model.Ticket;
//import com.example.test.service.TicketService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.util.*;
//
//import static com.example.test.dto.TicketDto.toDto;
//
//@Slf4j
//@RestController
//@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com", "https://www.dksolidwaste.com"}, allowCredentials = "true")
//@RequestMapping("/tickets")
//public class TicketController {
//
//
//    @Autowired
//    private TicketService ticketService;
//
//
//    @GetMapping("/item")
//    public List<TicketDto> findTicketsByOrderAndItemID(String item, Integer siteNo) {
//        List<Ticket> tickets = ticketService.findTicketsByOrderAndItemIDAndSiteNo(0, item, siteNo);
//        List<TicketDto> ticketDtos = new ArrayList<>();
//
//        for (Ticket ticket : tickets) {
//            TicketDto ticketDto = toDto(ticket);
//            ticketDtos.add(ticketDto);
//        }
//        return ticketDtos;
//    }
//
//
//    @GetMapping("/site")
//    public List<TicketDto> getTicketsBySiteNo(Integer siteNo) {
//        List<Ticket> tickets = ticketService.getTicketsBySiteNo(siteNo);
//        List<TicketDto> ticketDtos = new ArrayList<>();
//
//        for (Ticket ticket : tickets) {
//            TicketDto ticketDto = toDto(ticket);
//            ticketDtos.add(ticketDto);
//        }
//        return ticketDtos;
//    }
//
//
//    @GetMapping("/site-date")
//    public List<TicketDto> getTicketsBySiteNoAndDate(
//            @RequestParam("siteNo") Integer siteNo,
//            @RequestParam("selectedDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate selectedDate
//    ) {
//        List<Ticket> tickets = ticketService.getTicketsBySiteNoAndDate(siteNo, selectedDate);
//
//        List<TicketDto> ticketDtos = new ArrayList<>();
//
//        for (Ticket ticket : tickets) {
//            TicketDto ticketDto = toDto(ticket);
//            ticketDtos.add(ticketDto);
//        }
//        return ticketDtos;
//    }
//
//    @GetMapping("/itemType-date")
//    public List<TicketDto> getTicketsByItemTypeAndDate(
//            @RequestParam("itemType") String itemType,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) throws ParseException {
//        List<Ticket> tickets = ticketService.getTicketsByItemTypeAndDate(itemType, startDate, endDate);
//
//        List<TicketDto> ticketDtos = new ArrayList<>();
//
//        for (Ticket ticket : tickets) {
//            TicketDto ticketDto = toDto(ticket);
//            ticketDtos.add(ticketDto);
//        }
//        return ticketDtos;
//    }
//
//
//    @GetMapping("/itemType-site-date/total-netWeight")
//    public BigDecimal getTotalNetWeightByItemTypeSiteNoAndDate(
//            @RequestParam("itemType") String itemType,
//            @RequestParam("siteNo") Integer siteNo,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) {
//        BigDecimal totalNetWeight = ticketService.getTotalNetWeightByItemTypeSiteNoAndDate(itemType, siteNo, startDate, endDate);
//        return totalNetWeight;
//    }
//
//
//    @GetMapping("/itemName-date")
//    public List<TicketDto> getTicketsByItemNameAndDate(
//            @RequestParam("itemName") String itemName,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) {
//        List<Ticket> tickets = ticketService.getTicketsByItemNameAndDate(itemName, startDate, endDate);
//
//        List<TicketDto> ticketDtos = new ArrayList<>();
//
//        for (Ticket ticket : tickets) {
//            TicketDto ticketDto = toDto(ticket);
//            ticketDtos.add(ticketDto);
//        }
//        return ticketDtos;
//    }
//
//
//    @GetMapping("/itemId/weight")
//    public BigDecimal getTotalNetWeightByItemIdSiteNoAndDate(
//            @RequestParam("itemId") Integer itemId,
//            @RequestParam(value = "siteNo", required = false) Integer siteNo,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) {
//        BigDecimal totalNetWeight = ticketService.getTotalNetWeightByItemNameAndDate(itemId, siteNo, startDate, endDate);
//        return totalNetWeight;
//    }
//
//    @GetMapping("/itemType/weight")
//    public BigDecimal getTotalNetWeightByItemTypeAndDate(
//            @RequestParam("itemType") String itemType,
//            @RequestParam(value = "siteNo", required = false) Integer siteNo,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) {
//        BigDecimal totalNetWeight = ticketService.getTotalNetWeightByItemTypeAndDate(itemType, siteNo, startDate, endDate);
//        return totalNetWeight;
//    }
//
//    @GetMapping("/itemType/weight-list")
//    public BigDecimal[] getNetWeightsByItemTypeAndDate(
//            @RequestParam(value = "itemType") String itemType,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) {
//        BigDecimal[] netWeightsByItemType = ticketService.getNetWeightsByItemTypeAndDate(itemType, startDate, endDate);
//        return netWeightsByItemType;
//    }
//
//    @GetMapping("/itemId/weight-list")
//    public BigDecimal[] getNetWeightsByItemIdAndDate(
//            @RequestParam(value = "itemId") Integer itemId,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) {
//        BigDecimal[] netWeightsByItemName = ticketService.getNetWeightsByItemNameAndDate(itemId, startDate, endDate);
//        return netWeightsByItemName;
//    }
//
//    @GetMapping("/itemId-site/weight-date-list")
//    public Map<String, BigDecimal> getNetWeightByDate(
//            @RequestParam("itemId") Integer itemId,
//            @RequestParam(value = "siteNo", required = false) Integer siteNo,
//            @RequestParam(value = "centerId", required = false) Integer centerId,
//            @RequestParam(value = "villageId", required = false) Integer villageId,
//            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate startDate,
//            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MMM-yy") LocalDate endDate
//    ) {
//        Map<String, BigDecimal> netWeightMap = ticketService.getNetWeightByDate(itemId, siteNo, centerId, villageId, startDate, endDate);
//        return netWeightMap;
//    }
//
//    @PutMapping("/{ticketId}")
//    public void updateTicket(@PathVariable Integer ticketId, @RequestBody Map<String, Object> updates) {
//
//        ticketService.updateTicket(ticketId, updates);
//
//    }
//}
