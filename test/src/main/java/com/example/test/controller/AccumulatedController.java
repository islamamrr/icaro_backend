package com.example.test.controller;

import com.example.test.model.Accumulated;
import com.example.test.service.AccumulatedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com"}, allowCredentials = "true")
@RequestMapping("/accumulated")
public class AccumulatedController {
    @Autowired
    private AccumulatedService accumulatedService;

    @GetMapping("/percentage")
    public ResponseEntity<Map<String, Integer>> getAccumulatedPercentageBySiteNo(
            @RequestParam(value = "siteNo", required = false) Integer siteNo) {
        Map<String, Integer> result = accumulatedService.getAccumulatedPercentage(siteNo);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/weight")
    public ResponseEntity<Map<String, Integer>> getAccumulatedWeightBySiteNo(
            @RequestParam(value = "siteNo", required = false) Integer siteNo) {
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
