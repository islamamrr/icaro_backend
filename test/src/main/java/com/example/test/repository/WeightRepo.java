package com.example.test.repository;

import com.example.test.model.Weight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeightRepo extends JpaRepository<Weight, Integer> {
}
