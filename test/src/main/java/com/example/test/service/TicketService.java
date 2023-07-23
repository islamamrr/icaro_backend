//package com.example.test.service;
//
//import com.example.test.model.Ticket;
//import com.example.test.model.Weight;
//import com.example.test.repository.TicketRepo;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Slf4j
//@Service
//public class TicketService {
//
//    @Autowired
//    private TicketRepo ticketRepo;
//
//    public List<Ticket> findTicketsByOrderAndItemIDAndSiteNo(Integer order, String itemName, Integer siteNo) {
//        return ticketRepo.findByTicketIdOrderNotAndItemItemNameAndSiteNo(0, itemName, siteNo);
//    }
//
//    public List<Ticket> getTicketsBySiteNo(Integer siteNo) {
//        return ticketRepo.findBySiteNo(siteNo);
//    }
//
//
//    public List<Ticket> getAllTickets() {
//        return ticketRepo.findAll();
//    }
//
////    public BigInteger getNetWeightByItemID(Integer item) {
////        List<Ticket> tickets = ticketRepo.findByItemItemId(item);
////
////        BigInteger netWeight = BigInteger.ZERO;
////
////        for (Ticket ticket : tickets) {
////            netWeight = netWeight.add(ticket.getWeight().getNetWeight() == null ? BigInteger.ZERO : ticket.getWeight().getNetWeight());
////        }
////        return netWeight;
////    }
//
//    public List<Ticket> getTicketsBySiteNoAndDate(Integer siteNo, LocalDate selectedDate) {
//
//        String formattedSelectedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        return ticketRepo.findAllBySiteNoAndCarTwoDateBetween(siteNo, formattedSelectedDate);
//    }
//
//    public List<Ticket> getTicketsByItemTypeAndDate(String itemType, LocalDate startDate, LocalDate endDate) throws ParseException {
//
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        return ticketRepo.findAllByItemTypeAndCarTwoDateBetween(itemType, formattedStartDate, formattedEndDate);
//    }
//
//    public BigDecimal getTotalNetWeightByItemTypeSiteNoAndDate(String itemType, Integer siteNo, LocalDate startDate, LocalDate endDate) {
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        return ticketRepo.getTotalNetWeightByItemTypeSiteNoAndCarTwoDateBetween(itemType, siteNo, formattedStartDate, formattedEndDate);
//    }
//
//
//    public List<Ticket> getTicketsByItemNameAndDate(String itemName, LocalDate startDate, LocalDate endDate) {
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        return ticketRepo.findAllByItemNameAndCarTwoDateBetween(itemName, formattedStartDate, formattedEndDate);
//    }
//
////    public BigDecimal getTotalNetWeightByItemNameSiteNoAndDate(String itemName, Integer siteNo, LocalDate startDate, LocalDate endDate) {
////        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
////        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
////
////        return ticketRepo.getTotalNetWeightByItemNameSiteNoAndCarTwoDateBetween(itemName, siteNo, formattedStartDate, formattedEndDate);
////    }
//
//
//    public BigDecimal getTotalNetWeightByItemNameAndDate(Integer itemId, Integer siteNo, LocalDate startDate, LocalDate endDate) {
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        if (siteNo != null) {
//            return ticketRepo.getTotalNetWeightByItemIdSiteNoAndCarTwoDateBetween(itemId, siteNo, formattedStartDate, formattedEndDate);
//        } else {
//            return ticketRepo.getTotalNetWeightByItemIdAndCarTwoDateBetween(itemId, formattedStartDate, formattedEndDate);
//        }
//    }
//
//    public BigDecimal getTotalNetWeightByItemTypeAndDate(String itemType, Integer siteNo, LocalDate startDate, LocalDate endDate) {
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        if (siteNo != null) {
//            return ticketRepo.getTotalNetWeightByItemTypeSiteNoAndCarTwoDateBetween(itemType, siteNo, formattedStartDate, formattedEndDate);
//        } else {
//            return ticketRepo.getTotalNetWeightByItemTypeAndCarTwoDateBetween(itemType, formattedStartDate, formattedEndDate);
//        }
//    }
//
//    public BigDecimal[] getNetWeightsByItemTypeAndDate(String itemType, LocalDate startDate, LocalDate endDate) {
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        BigDecimal[] netWeightsByItemType = new BigDecimal[6];
//        for (int i = 1; i <= 6; i++) {
//            Integer siteNo = i;
//            BigDecimal netWeight = ticketRepo.getNetWeightByItemTypeAndSiteNoAndCarTwoDateBetween(itemType, siteNo, formattedStartDate, formattedEndDate);
//            netWeightsByItemType[i - 1] = netWeight;
//        }
//        return netWeightsByItemType;
//    }
//
//    public BigDecimal[] getNetWeightsByItemNameAndDate(Integer itemId, LocalDate startDate, LocalDate endDate) {
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        BigDecimal[] netWeightsByItemName = new BigDecimal[6];
//        for (int i = 1; i <= 6; i++) {
//            Integer siteNo = i;
//            BigDecimal netWeight = ticketRepo.getNetWeightByItemIdAndSiteNoAndCarTwoDateBetween(itemId, siteNo, formattedStartDate, formattedEndDate);
//            netWeightsByItemName[i - 1] = netWeight;
//        }
//        return netWeightsByItemName;
//    }
//
//    //    public Map<String, BigDecimal> getNetWeightByDate(String itemName, Integer siteNo, LocalDate startDate, LocalDate endDate) {
//    public Map<String, BigDecimal> getNetWeightByDate(Integer itemId, Integer siteNo, Integer centerId, Integer villageId, LocalDate startDate, LocalDate endDate) {
//        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        List<Object> results;
////        results = ticketRepo.getNetWeightByItemNameSiteNoAndCarTwoDateBetween(itemName, siteNo, formattedStartDate, formattedEndDate);
//        results = ticketRepo.getNetWeightByItemIdSiteNoCenterIdVillageIdAndCarTwoDateBetween(itemId, siteNo, centerId, villageId, formattedStartDate, formattedEndDate);
//
//        Map<String, BigDecimal> netWeightMap = new LinkedHashMap<>();
//
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM");
//        LocalDate currentDate = startDate;
//        while (!currentDate.isAfter(endDate)) {
//            String formattedDate = currentDate.format(dateFormatter);
//            netWeightMap.put(formattedDate, BigDecimal.ZERO);
//            currentDate = currentDate.plusDays(1);
//        }
//
//        for (Object result : results) {
//            Object[] row = (Object[]) result;
//            String dateString = (String) row[0];
//            BigInteger netWeightBigInteger = (BigInteger) row[1];
//            BigDecimal netWeight = new BigDecimal(netWeightBigInteger);
//            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MMM-yy"));
//            String formattedDate = date.format(dateFormatter);
//            netWeightMap.put(formattedDate, netWeight);
//        }
//
//        return netWeightMap;
//    }
//
//    public Ticket getHighestTicketIdOrder() {
//        Ticket x = ticketRepo.findTicketWithMaxTicketIdOrder();
//        log.info("HENA" + x.getTicketIdOrder());
//        return x;
//    }
//
//    public void updateTicket(Integer ticketId, Map<String, Object> updates) {
//        Optional<Ticket> optionalTicket = ticketRepo.findById(ticketId);
//
//        Ticket ticket = optionalTicket.get();
//        Weight weight = ticket.getWeight();
//
//        // Apply the updates to the ticket object
//
//        for (Map.Entry<String, Object> entry : updates.entrySet()) {
//            String columnName = entry.getKey();
//            Object columnValue = entry.getValue();
//
//            switch (columnName) {
//                case "carTwoDate":
//                    weight.setCarTwoDate((String) columnValue);
//                    break;
//                case "netWeight":
//                    weight.setNetWeight(BigInteger.valueOf((Integer) columnValue));
//                    break;
//                case "carTwoTime":
//                    weight.setCarTwoTime((String) columnValue);
//                    break;
//                case "secondWeightCar":
//                    weight.setSecondWeightCar((String) columnValue);
//                    break;
//            }
//        }
//
//        Ticket maxTicket = getHighestTicketIdOrder();
//        if (!Objects.equals(maxTicket.getTicketId(), ticketId))
//            ticket.setTicketIdOrder(maxTicket.getTicketIdOrder() + 1);
//
//        ticket.setEnterMethod("dashboard");
//
//        ticketRepo.save(ticket);
//    }
//}
