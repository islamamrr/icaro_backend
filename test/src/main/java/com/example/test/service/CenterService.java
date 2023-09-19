package com.example.test.service;

import com.example.test.model.Center;
import com.example.test.repository.CenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CenterService {

    @Autowired
    private CenterRepo centerRepo;

    public List<Center> getAllCenters() {
        List<Integer> excludedCenterIds = Arrays.asList(24, 25);
        return centerRepo.findAllByCenterIdNotIn(excludedCenterIds);
    }
}
