package com.example.test.controller;

import com.example.test.model.Center;
import com.example.test.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com"}, allowCredentials = "true")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @GetMapping("/centers")
    public List<Center> getAllCenters() {
        return centerService.getAllCenters();
    }

    @PutMapping("/update-targets")
    public ResponseEntity<String> updateCenterTargets(@RequestBody Map<Integer, Integer> targetMap) {
        boolean updated = centerService.updateCenterTargets(targetMap);

        if (updated) {
            return ResponseEntity.ok("Targets updated successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to update targets.");
        }
    }

    @GetMapping("/targets")
    public Map<Integer, Integer> getAllTargets() {
        return centerService.getAllTargets();
    }
}
