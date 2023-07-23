package com.example.test.service;

import com.example.test.repository.WeightRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeightService {
    @Autowired
    private WeightRepo repo;
}
