package com.example.test.controller;

import com.example.test.model.Center;
import com.example.test.service.CenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com"}, allowCredentials = "true")
public class CenterController {

    @Autowired
    private CenterService centerService;

    @GetMapping("/centers")
    public List<Center> getAllCenters() {
        return centerService.getAllCenters();
    }
}
