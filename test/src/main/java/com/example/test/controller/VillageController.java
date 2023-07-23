package com.example.test.controller;

import com.example.test.model.Village;
import com.example.test.service.VillageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com"}, allowCredentials = "true")
public class VillageController {
    @Autowired
    private VillageService villageService;

    @GetMapping("/villages")
    public List<Village> getVillagesByCenterId(
            @RequestParam("centerId") Integer centerId) {
        return villageService.getVillagesByCenterId(centerId);
    }
}
