package com.example.test.service;

import com.example.test.dto.TicketWeightUpdateRequest;
import com.example.test.model.Center;
import com.example.test.model.CompositeKey;
import com.example.test.model.TicketWeight;
import com.example.test.repository.CenterRepo;
import com.example.test.repository.TicketWeightRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class TicketWeightService {

    @Autowired
    private TicketWeightRepo ticketWeightRepo;
    @Autowired
    private CenterRepo centerRepo;

    public BigDecimal getTotalNetWeightByItemNameAndDate(String itemName, Integer siteNo, String clientType, LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        BigDecimal weightKg = ticketWeightRepo.getTotalNetWeightByItemNameSiteNoAndCarTwoDateBetween(itemName, siteNo, clientType, formattedStartDate, formattedEndDate);
        if (weightKg != null)
            return weightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN);
        else
            return null;
    }

    public BigDecimal getTotalOutputRejectedNetWeightSiteNoAndDate(String clientType, LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        BigDecimal weightKg = ticketWeightRepo.getTotalNetWeightByItemNameSiteNoAndCarTwoDateBetween(null, 3, clientType, formattedStartDate, formattedEndDate);
        if (weightKg != null)
            return weightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN);
        else
            return null;
    }

    public BigDecimal getTotalNetWeightByItemTypeAndDate(String itemType, Integer siteNo, LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        BigDecimal weightKg = ticketWeightRepo.getTotalNetWeightByItemTypeSiteNoAndCarTwoDateBetween(itemType, siteNo, formattedStartDate, formattedEndDate);
        if (weightKg != null)
            return weightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN);
        else
            return null;
    }

    public BigDecimal getTotalOutputNetWeightBySiteNoAndDate(LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        String itemType = "مخرجات";

        BigDecimal outputWeightKg = ticketWeightRepo.getTotalNetWeightByItemTypeSiteNoAndCarTwoDateBetween(itemType, null, formattedStartDate, formattedEndDate);
        BigDecimal marfoodatWeightKg = ticketWeightRepo.getTotalNetWeightByItemTypeSiteNoAndCarTwoDateBetween(null, 3, formattedStartDate, formattedEndDate);

        if (outputWeightKg == null)
            outputWeightKg = BigDecimal.ZERO;
        if (marfoodatWeightKg == null)
            marfoodatWeightKg = BigDecimal.ZERO;

        BigDecimal outputWeight = outputWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN);
        BigDecimal marfoodatWeight = marfoodatWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN);

        return outputWeight.add(marfoodatWeight);
    }

    public Map<String, BigDecimal> getDateNetWeightsMapByItemNameVillageCenter(String itemName, Integer siteNo, Integer centerId, Integer villageId, String clientType, LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        if (clientType == null || clientType.isEmpty()) {
            clientType = null;
        }
        if (itemName == null || itemName.isEmpty()) {
            itemName = null;
        }

        List<Object> results;
        results = ticketWeightRepo.getDateNetWeightsMapByItemNameVillageCenter(itemName, siteNo, centerId, villageId, clientType, formattedStartDate, formattedEndDate);

        Map<String, BigDecimal> netWeightMap = new LinkedHashMap<>();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MMM");
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            String formattedDate = currentDate.format(dateFormatter);
            netWeightMap.put(formattedDate, BigDecimal.ZERO);
            currentDate = currentDate.plusDays(1);
        }

        for (Object result : results) {
            Object[] row = (Object[]) result;
            String dateString = (String) row[0];
            Long netWeightLong = (Long) row[1];
            BigInteger netWeightBigInteger = BigInteger.valueOf(netWeightLong);
            BigDecimal netWeightKg = new BigDecimal(netWeightBigInteger);
            BigDecimal netWeight = netWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN);
            LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd-MMM-yy"));
            String formattedDate = date.format(dateFormatter);
            netWeightMap.put(formattedDate, netWeight);
        }

        return netWeightMap;
    }

    public List<TicketWeight> getTicketsBySiteNoAndDate(Integer siteNo, LocalDate selectedDate) {

        String formattedSelectedDate = selectedDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        return ticketWeightRepo.findAllBySiteNoAndDateBetween(siteNo, formattedSelectedDate);
    }

    public BigDecimal[] getNetWeightsByItemTypeAndDate(String itemType, LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        BigDecimal[] netWeightsByItemType = new BigDecimal[5];
        for (int i = 1; i <= 6; i++) {
            if (i == 3) // المدفن
                continue;
            Integer siteNo = i;
            BigDecimal netWeightKg = ticketWeightRepo.getAllNetWeightByItemTypeAndDate(itemType, siteNo, formattedStartDate, formattedEndDate);
            BigDecimal netWeight = (!Objects.equals(netWeightKg, BigDecimal.ZERO) && netWeightKg != null) ? netWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN) : null;
            if (i > 3)
                netWeightsByItemType[i - 2] = netWeight;
            else
                netWeightsByItemType[i - 1] = netWeight;
        }
        return netWeightsByItemType;
    }

    public BigDecimal[] getOutputNetWeightsByDate(LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        String itemType = "مخرجات";
        String[] clientTypes = {"مصنع اجا", "مصنع سندوب", "", "مصنع بلقاس", "مصنع السمبلاوين", "مصنع المنزلة"};

        BigDecimal[] netWeightsByItemType = new BigDecimal[5];
        for (int i = 1; i <= 6; i++) {
            if (i == 3) // المدفن
                continue;
            Integer siteNo = i;
            BigDecimal netWeightKg = ticketWeightRepo.getAllNetWeightByItemTypeAndDate(itemType, siteNo, formattedStartDate, formattedEndDate);
            BigDecimal netWeight = (!Objects.equals(netWeightKg, BigDecimal.ZERO) && netWeightKg != null) ? netWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN) : null;

            BigDecimal rejectedNetWeight = getTotalNetWeightByItemNameAndDate(null, 3, clientTypes[i - 1], startDate, endDate);
            if (rejectedNetWeight == null)
                rejectedNetWeight = BigDecimal.ZERO;

            if (netWeight == null)
                netWeight = BigDecimal.ZERO;

            if (i > 3)
                netWeightsByItemType[i - 2] = netWeight.add(rejectedNetWeight);
            else
                netWeightsByItemType[i - 1] = netWeight.add(rejectedNetWeight);

        }
        return netWeightsByItemType;

    }

//    public TicketWeight getHighestTicketId(Integer siteNo) {
//        TicketWeight maxTicketId = ticketWeightRepo.findTicketWithMaxTicketIdBySiteNo(siteNo);
//        return maxTicketId;
//    }
//
//    public TicketWeight updateTicket(Integer ticketId, Integer siteNo, Map<String, Object> updates) {
//        CompositeKey compositeKey = new CompositeKey(ticketId, siteNo);
//        Optional<TicketWeight> optionalTicketWeight = ticketWeightRepo.findById(compositeKey);
//
//
//        TicketWeight ticketWeight;
//
//        if (optionalTicketWeight.isPresent()) {
//            ticketWeight = optionalTicketWeight.get();
//        } else {
//            // If the entity doesn't exist, create a new instance with the given composite key
//            ticketWeight = new TicketWeight();
//            ticketWeight.setId(compositeKey);
//        }
//
//        for (Map.Entry<String, Object> entry : updates.entrySet()) {
//            String columnName = entry.getKey();
//            Object columnValue = entry.getValue();
//
//            switch (columnName) {
//                case "carTwoDate":
//                    ticketWeight.setCarTwoDate((String) columnValue);
//                    break;
//                case "netWeight":
//                    ticketWeight.setNetWeight((Integer) columnValue);
//                    break;
//                case "carTwoTime":
//                    ticketWeight.setCarTwoTime((String) columnValue);
//                    break;
//                case "secondWeight":
//                    ticketWeight.setSecondWeight((Integer) columnValue);
//                    break;
//            }
//        }
//
//
//        TicketWeight maxTicketWeight = getHighestTicketId(siteNo);
//
////        if (!Objects.equals(maxTicketWeight.getId().getTicketId(), ticketId)) {
//            ticketWeight.getId().setTicketId(maxTicketWeight.getId().getTicketId() + 1);
////        }
//
//        ticketWeight.setEnterMethod("dashboard");
//
//        ticketWeightRepo.save(ticketWeight);
////        ticketWeightRepo.deleteById(new CompositeKey(ticketId, siteNo));
//
//        return ticketWeight;
//    }

    public TicketWeight createTicketWeight(TicketWeight ticketWeight) {
        return ticketWeightRepo.save(ticketWeight);
    }


    public TicketWeight updateTicket(Integer ticketId, Integer siteNo, TicketWeightUpdateRequest updateRequest) {
        TicketWeight existingTicket = ticketWeightRepo.findById(new CompositeKey(ticketId, siteNo))
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));

        TicketWeight newTicket = new TicketWeight();

        int maxTicketId = getMaxTicketId(siteNo);
        if (existingTicket.getId().getTicketId().equals(maxTicketId)) {
            newTicket.setId(existingTicket.getId()); // Keep the same ticketId
        } else {
            newTicket.setId(new CompositeKey(maxTicketId + 1, siteNo)); // Increment ticketId
        }

        newTicket.setClientName(existingTicket.getClientName());
        newTicket.setClientType(existingTicket.getClientType());
        newTicket.setDriverName(existingTicket.getDriverName());
        newTicket.setItemName(existingTicket.getItemName());
        newTicket.setItemType(existingTicket.getItemType());
        newTicket.setVehicleNumber(existingTicket.getVehicleNumber());
        newTicket.setNotes(existingTicket.getNotes());
        newTicket.setCenter(existingTicket.getCenter());
        newTicket.setVillage(existingTicket.getVillage());
        newTicket.setFirstWeight(existingTicket.getFirstWeight());
        newTicket.setCarOneDate(existingTicket.getCarOneDate());
        newTicket.setCarOneTime(existingTicket.getCarOneTime());
        newTicket.setSecondWeight(updateRequest.getSecondWeight());
        newTicket.setNetWeight(updateRequest.getNetWeight());
        newTicket.setCarTwoDate(updateRequest.getCarTwoDate());
        newTicket.setCarTwoTime(updateRequest.getCarTwoTime());

        newTicket.setEnterMethod("dashboard");
        ticketWeightRepo.delete(existingTicket);
        ticketWeightRepo.save(newTicket);

        return newTicket;
    }

    private Integer getMaxTicketId(Integer siteNo) {
        TicketWeight maxTicket = ticketWeightRepo.findTicketWithMaxTicketIdBySiteNo(siteNo);
        return maxTicket.getId().getTicketId();
    }

    public BigDecimal[] getNetWeightsByItemNameAndDate(String itemName, LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        BigDecimal[] netWeightsByItemName = new BigDecimal[5];
        for (int i = 1; i <= 6; i++) {
            if (i == 3) // المدفن
                continue;
            Integer siteNo = i;
            BigDecimal netWeightKg = ticketWeightRepo.getAllNetWeightByItemNameAndDate(itemName, null, siteNo, formattedStartDate, formattedEndDate);
            BigDecimal netWeight = (!Objects.equals(netWeightKg, BigDecimal.ZERO) && netWeightKg != null) ? netWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN) : null;

            if (i > 3)
                netWeightsByItemName[i - 2] = netWeight;
            else
                netWeightsByItemName[i - 1] = netWeight;
        }
        return netWeightsByItemName;
    }

    public BigDecimal[] getOutputRejectedNetWeightsByDate(LocalDate startDate, LocalDate endDate) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        String[] clientTypes = {"مصنع اجا", "مصنع سندوب", "", "مصنع بلقاس", "مصنع السمبلاوين", "مصنع المنزلة"};
        BigDecimal[] netWeightsByItemName = new BigDecimal[5];
        for (int i = 1; i <= 6; i++) {
            if (i == 3) // المدفن
                continue;

            BigDecimal netWeightKg = ticketWeightRepo.getAllNetWeightByItemNameAndDate(null, clientTypes[i - 1], 3, formattedStartDate, formattedEndDate);
            BigDecimal netWeight = (!Objects.equals(netWeightKg, BigDecimal.ZERO) && netWeightKg != null) ? netWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN) : null;

            if (i > 3)
                netWeightsByItemName[i - 2] = netWeight;
            else
                netWeightsByItemName[i - 1] = netWeight;
        }
        return netWeightsByItemName;
    }

    public Map<String, BigDecimal> getCenterNetWeights(LocalDate startDate, LocalDate endDate, String itemType) {
        String formattedStartDate = startDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));

        List<Object[]> results = ticketWeightRepo.getCenterNetWeights(formattedStartDate, formattedEndDate, itemType);
        Map<String, BigDecimal> centerNetWeights = new HashMap<>();

        Set<String> processedCenterNames = new HashSet<>();

        for (Object[] result : results) {
            String centerName = (String) result[0];
            Long netWeightLong = (Long) result[1];

            BigDecimal netWeightKg = (netWeightLong != null) ? BigDecimal.valueOf(netWeightLong) : BigDecimal.ZERO;
            BigDecimal netWeight = netWeightKg.divide(new BigDecimal(1000), 1, RoundingMode.HALF_DOWN);

            centerNetWeights.put(centerName, netWeight);

            processedCenterNames.add(centerName);
        }

        // Iterate through all centers to add those that were not in the result set with netWeight set to zero
        // This ensures that all centers are included in the response
        List<Center> allCenterNames = getAllCenters(); // You need to implement this method to fetch all center names

        for (Center centerName : allCenterNames) {
            if (!processedCenterNames.contains(centerName.getCenterName())) {
                centerNetWeights.put(centerName.getCenterName(), BigDecimal.ZERO);
            }
        }

        return centerNetWeights;
    }

    public List<Center> getAllCenters() {
        return centerRepo.findAll();
    }
}
