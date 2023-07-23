package com.example.test.repository;

import com.example.test.model.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VillageRepo extends JpaRepository<Village, Integer> {
    List<Village> findByCenterCenterId(Integer centerId);
}
