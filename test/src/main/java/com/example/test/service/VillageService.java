package com.example.test.service;

import com.example.test.model.Village;
import com.example.test.repository.VillageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VillageService {
    @Autowired
    private VillageRepo villageRepo;

    public List<Village> getVillagesByCenterId(Integer centerId) {
        return villageRepo.findByCenterCenterId(centerId);
    }
}
