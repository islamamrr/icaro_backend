package com.example.test.controller;

import com.example.test.service.AccumulatedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com", "https://www.dksolidwaste.com"}, allowCredentials = "true")
@RequestMapping("/accumulated")
public class AccumulatedController {
    @Autowired
    private AccumulatedService accumulatedService;

    @GetMapping("/percentage")
    @Cacheable(value = "getAccumulatedWeightBySiteNo")
    public ResponseEntity<Map<String, Integer>> getAccumulatedPercentageBySiteNo(
            @RequestParam(value = "siteNo", required = false) Integer siteNo) {
        Map<String, Integer> result = accumulatedService.getAccumulatedPercentage(siteNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/percentage-by-itemname")
    @Cacheable(value = "getAccumulatedPercentageByItemName")
    public ResponseEntity<Map<Integer, Integer>> getAccumulatedPercentageByItemName(
            @RequestParam(value = "itemName", required = false) String itemName) {
        Map<Integer, Integer> result = accumulatedService.getAccumulatedPercentageByItemName(itemName);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/weight")
    @Cacheable(value = "getAccumulatedWeightBySiteNo")
    public ResponseEntity<Map<String, Integer>> getAccumulatedWeightBySiteNo(
            @RequestParam(value = "siteNo", required = false) Integer siteNo) {
        log.info("ana ahooooo");
        Map<String, Integer> result = accumulatedService.getAccumulatedWeight(siteNo);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/update-percentage/{siteNo}")
    public ResponseEntity<String> updatePercentages(
            @PathVariable Integer siteNo,
            @RequestBody Map<String, Integer> percentageUpdates
    ) {
        try {
            accumulatedService.updatePercentages(siteNo, percentageUpdates);
            return ResponseEntity.ok("Percentages updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update percentages: " + e.getMessage());
        }
    }

    @PostMapping("/update-weight/{siteNo}")
    public ResponseEntity<String> updateWeight(
            @PathVariable Integer siteNo,
            @RequestBody Map<String, Integer> weightUpdates
    ) {
        try {
            accumulatedService.updateWeights(siteNo, weightUpdates);
            return ResponseEntity.ok("Accumulated weights updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to update accumulated weights: " + e.getMessage());
        }
    }
}
