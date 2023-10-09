package com.example.test.service;

import com.example.test.model.Accumulated;
import com.example.test.model.AccumulatedCompositeKey;
import com.example.test.repository.AccumulatedRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @Scheduled(cron = "0 0 23 * * ?", zone = "Africa/Cairo")
    public void performTask() {
        Date d = new Date();
        TimeZone tZ = TimeZone.getDefault();
        SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dF.setTimeZone(tZ);
        String fd = dF.format(d);
        log.info("Formatted Date and Time: " + fd);


        String acceptedInputName = "مخلفات  تصلح للمعالجة";
        String asmedaName = "اسمدة عضوية";
        String waqoodName = "وقود بديل";
        String marfoodatName = "مرفوضات";
        String mafroozatName = "مفروزات";

        LocalDate currentDate = LocalDate.now();

        BigDecimal[] acceptedInputsList = ticketWeightService.getNetWeightsByItemNameAndDate(acceptedInputName, currentDate, currentDate);
        BigDecimal[] asmedaWeightsList = ticketWeightService.getNetWeightsByItemNameAndDate(asmedaName, currentDate, currentDate);
        BigDecimal[] waqoodWeightsList = ticketWeightService.getNetWeightsByItemNameAndDate(waqoodName, currentDate, currentDate);
        BigDecimal[] marfoodatWeightsList = ticketWeightService.getOutputRejectedNetWeightsByDate(currentDate, currentDate);
        BigDecimal[] mafroozatWeightsList = ticketWeightService.getNetWeightsByItemNameAndDate(mafroozatName, currentDate, currentDate);

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

            if (i >= 2) {
                updateWeights(i + 2, updates);
            } else {
                updateWeights(i + 1, updates);
            }
        }
    }
}
