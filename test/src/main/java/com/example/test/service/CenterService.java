package com.example.test.service;

import com.example.test.model.Center;
import com.example.test.repository.CenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CenterService {

    @Autowired
    private CenterRepo centerRepo;

    public List<Center> getAllCenters() {
        return centerRepo.findAll();
    }

}
