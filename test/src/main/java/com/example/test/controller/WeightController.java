package com.example.test.controller;


import com.example.test.service.WeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com/"}, allowCredentials = "true")
@RequestMapping("/weight")
public class WeightController {

    @Autowired
    private WeightService weightService;

}
