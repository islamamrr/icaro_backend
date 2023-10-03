package com.example.test.service;

import com.example.test.model.Accumulated;
import com.example.test.model.AccumulatedCompositeKey;
import com.example.test.model.Center;
import com.example.test.repository.AccumulatedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AccumulatedService {
    @Autowired
    private AccumulatedRepo accumulatedRepo;

    public Map<String, Integer> getAccumulatedPercentage(Integer siteNo) {
        List<Object[]> results = accumulatedRepo.findAccumulatedPercentage(siteNo);

        Map<String, Integer> result = new HashMap<>();

        for (Object[] row : results) {
            result.put((String) row[0], (Integer) row[1]);
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
        }
        else {
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
}
