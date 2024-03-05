package com.example.test.service;

import com.example.test.model.Center;
import com.example.test.repository.CenterRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CenterService {

    @Autowired
    private CenterRepo centerRepo;

    public List<Center> getAllCenters() {
        List<Integer> excludedCenterIds = Arrays.asList(24, 25);
        return centerRepo.findAllByCenterIdNotIn(excludedCenterIds);
    }

    public boolean updateCenterTargets(Map<Integer, Integer> targetMap) {
        try {
            for (Map.Entry<Integer, Integer> entry : targetMap.entrySet()) {
                Integer centerId = entry.getKey();
                Integer newTarget = entry.getValue();

                Optional<Center> optionalCenter = centerRepo.findById(centerId);

                if (optionalCenter.isPresent()) {
                    Center center = optionalCenter.get();
                    center.setTarget(newTarget);
                    centerRepo.save(center);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Map<String, Integer> getAllTargets() {
        List<Center> centers = centerRepo.findAllByCenterIdNotIn(Arrays.asList(24, 25));
//        List<Center> sortedCenters = centers.stream()
//                .sorted(Comparator.comparingInt(Center::getCenterId))
//                .collect(Collectors.toList());

        Map<String, Integer> targetsMap = new HashMap<>();

        for (Center center : centers) {
            targetsMap.put(center.getCenterName(), center.getTarget());
        }

        log.info("targetsMap");
        log.info(targetsMap.toString());
        return targetsMap;
    }
}
