package com.example.test.repository;

import com.example.test.model.Center;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CenterRepo extends JpaRepository<Center, Integer> {
    List<Center> findAllByCenterIdNotIn(List<Integer> centerIds);
}
