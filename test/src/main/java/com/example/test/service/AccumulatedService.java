package com.example.test.service;

import com.example.test.model.Accumulated;
import com.example.test.model.AccumulatedCompositeKey;
import com.example.test.repository.AccumulatedRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class AccumulatedService {
    @Autowired
    private AccumulatedRepo accumulatedRepo;
    @Autowired
    TicketWeightService ticketWeightService;

    public Map<String, Integer> getAccumulatedPercentage(Integer siteNo) {
        List<Object[]> results = accumulatedRepo.findAccumulatedPercentage(siteNo);

        Map<String, Integer> result = new HashMap<>();

        for (Object[] row : results) {
            result.put((String) row[0], (Integer) row[1]);
        }

        return result;
    }

    public Map<Integer, Integer> getAccumulatedPercentageByItemName(String itemName) {
        List<Object[]> results = accumulatedRepo.findAccumulatedPercentageByItemName(itemName);

        Map<Integer, Integer> result = new HashMap<>();

        for (Object[] row : results) {
            result.put((Integer) row[0], (Integer) row[1]);
        }

        return result;
    }

    public Map<String, Integer> getAccumulatedWeight(Integer siteNo) {
        List<Object[]> results;
        Map<String, Integer> result = new HashMap<>();

        if (siteNo != null) {
            results = accumulatedRepo.findAccumulatedWeight(siteNo);
            for (Object[] row : results) {
                result.put((String) row[0], (Integer) row[1]);
            }
        } else {
            results = accumulatedRepo.findSumWeight();
            for (Object[] row : results) {
                result.put((String) row[0], ((Long) row[1]).intValue());
            }
        }
        return result;
    }

    @Cacheable
    public void updatePercentages(Integer siteNo, Map<String, Integer> updates) {
        for (Map.Entry<String, Integer> entry : updates.entrySet()) {
            String itemName = entry.getKey();
            Integer percentage = entry.getValue();

            Accumulated accumulated = accumulatedRepo.findById(new AccumulatedCompositeKey(siteNo, itemName))
                    .orElseThrow(() -> new EntityNotFoundException("Accumulated record not found"));

            accumulated.setPercentage(percentage);

            accumulatedRepo.save(accumulated);
        }
    }

    public void updateWeights(Integer siteNo, Map<String, Integer> updates) {
        for (Map.Entry<String, Integer> entry : updates.entrySet()) {
            String itemName = entry.getKey();
            Integer accumulatedWeight = entry.getValue();

            Accumulated accumulated = accumulatedRepo.findById(new AccumulatedCompositeKey(siteNo, itemName))
                    .orElseThrow(() -> new EntityNotFoundException("Accumulated record not found"));

            accumulated.setAccumulatedWeight(accumulatedWeight);

            accumulatedRepo.save(accumulated);
        }
    }

    @Scheduled(cron = "0 00 23 * * ?", zone = "Africa/Cairo")
    public void dailyUpdateAccumulatedWeights() {
        Date d = new Date();
        TimeZone tZ = TimeZone.getDefault();
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dF.setTimeZone(tZ);
        String fd = dF.format(d);
        log.info("Formatted Date and Time: " + fd);
        log.info("%%%%%% ننننن %%%%%%");

        String acceptedInputName = "مخلفات  تصلح للمعالجة";
        String asmedaName = "اسمدة عضوية";
        String waqoodName = "وقود بديل";
        String marfoodatName = "مرفوضات";
        String mafroozatName = "مفروزات";

        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDate currentDate = LocalDate.now();
        //Using yesterday's date as the updates would happen after midnight
        LocalDate yesterdayDate = currentDate.minusDays(1);

        BigDecimal[] acceptedInputsList = ticketWeightService.getNetWeightsByItemNameAndDate(acceptedInputName, yesterdayDate, yesterdayDate);
        BigDecimal[] asmedaWeightsList = ticketWeightService.getNetWeightsByItemNameAndDate(asmedaName, yesterdayDate, yesterdayDate);
        BigDecimal[] waqoodWeightsList = ticketWeightService.getNetWeightsByItemNameAndDate(waqoodName, yesterdayDate, yesterdayDate);
        BigDecimal[] marfoodatWeightsList = ticketWeightService.getOutputRejectedNetWeightsByDate(yesterdayDate, yesterdayDate);
        BigDecimal[] mafroozatWeightsList = ticketWeightService.getNetWeightsByItemNameAndDate(mafroozatName, yesterdayDate, yesterdayDate);

        log.info("yesterdayDate");
        log.info(String.valueOf(yesterdayDate));
        log.info("currentDateTime");
        log.info(String.valueOf(currentDateTime));

        log.info("acceptedInputsList");
        log.info(Arrays.toString(acceptedInputsList));
        log.info("asmedaWeightsList");
        log.info(Arrays.toString(asmedaWeightsList));
        log.info("waqoodWeightsList");
        log.info(Arrays.toString(waqoodWeightsList));
        log.info("marfoodatWeightsList");
        log.info(Arrays.toString(marfoodatWeightsList));
        log.info("mafroozatWeightsList");
        log.info(Arrays.toString(mafroozatWeightsList));

        for (int i = 0; i < acceptedInputsList.length; i++) {
            Map<String, Integer> percentages = new HashMap<>();
            Map<String, Integer> accumulatedWeights = new HashMap<>();

            if (i >= 2) {
                percentages = getAccumulatedPercentage(i + 2);
                accumulatedWeights = getAccumulatedWeight(i + 2);
            } else {
                percentages = getAccumulatedPercentage(i + 1);
                accumulatedWeights = getAccumulatedWeight(i + 1);
            }

            BigDecimal asmedaNewAccumulatedWeight = (acceptedInputsList[i]
                    .multiply(BigDecimal.valueOf(percentages.get(asmedaName))))
                    .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_DOWN)
                    .add(BigDecimal.valueOf(accumulatedWeights.get(asmedaName)))
                    .subtract(asmedaWeightsList[i]);

            BigDecimal waqoodNewAccumulatedWeight = (acceptedInputsList[i]
                    .multiply(BigDecimal.valueOf(percentages.get(waqoodName))))
                    .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_DOWN)
                    .add(BigDecimal.valueOf(accumulatedWeights.get(waqoodName)))
                    .subtract(waqoodWeightsList[i]);

            BigDecimal marfoodatNewAccumulatedWeight = (acceptedInputsList[i]
                    .multiply(BigDecimal.valueOf(percentages.get(marfoodatName))))
                    .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_DOWN)
                    .add(BigDecimal.valueOf(accumulatedWeights.get(marfoodatName)))
                    .subtract(marfoodatWeightsList[i]);

            BigDecimal mafroozatNewAccumulatedWeight = (acceptedInputsList[i]
                    .multiply(BigDecimal.valueOf(percentages.get(mafroozatName))))
                    .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_DOWN)
                    .add(BigDecimal.valueOf(accumulatedWeights.get(mafroozatName)))
                    .subtract(mafroozatWeightsList[i]);

            Map<String, Integer> updates = new HashMap<>();
            updates.put(asmedaName, asmedaNewAccumulatedWeight.intValue());
            updates.put(waqoodName, waqoodNewAccumulatedWeight.intValue());
            updates.put(marfoodatName, marfoodatNewAccumulatedWeight.intValue());
            updates.put(mafroozatName, mafroozatNewAccumulatedWeight.intValue());

            log.info("هههه");
            log.info("asmedaNewAccumulatedWeight");
            log.info(String.valueOf(asmedaNewAccumulatedWeight));
            log.info("waqoodNewAccumulatedWeight");
            log.info(String.valueOf(waqoodNewAccumulatedWeight));
            log.info("marfoodatNewAccumulatedWeight");
            log.info(String.valueOf(marfoodatNewAccumulatedWeight));
            log.info("mafroozatNewAccumulatedWeight");
            log.info(String.valueOf(mafroozatNewAccumulatedWeight));

            if (i >= 2) {
                log.info("site #" + (i + 2));
                updateWeights(i + 2, updates);
            } else {
                log.info("site #" + (i + 1));
                updateWeights(i + 1, updates);
            }
        }
//        return String.valueOf(currentDateTime);
    }
}
