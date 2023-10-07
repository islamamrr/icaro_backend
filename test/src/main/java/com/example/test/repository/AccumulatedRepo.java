package com.example.test.repository;

import com.example.test.model.Accumulated;
import com.example.test.model.AccumulatedCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface AccumulatedRepo extends JpaRepository<Accumulated, AccumulatedCompositeKey> {
    @Query("SELECT a.id.itemName, a.percentage FROM Accumulated a " +
            "WHERE (:siteNo IS NULL OR a.id.siteNo = :siteNo)")
    List<Object[]> findAccumulatedPercentage(
            @Param("siteNo") Integer siteNo);

    @Query("SELECT a.id.siteNo, a.percentage FROM Accumulated a " +
            "WHERE (:itemName IS NULL OR a.id.itemName = :itemName)")
    List<Object[]> findAccumulatedPercentageByItemName(
            @Param("itemName") String itemName);

    @Query("SELECT a.id.itemName, a.accumulatedWeight FROM Accumulated a WHERE :siteNo IS NULL OR a.id.siteNo = :siteNo")
    List<Object[]> findAccumulatedWeight(@Param("siteNo") Integer siteNo);

    @Query("SELECT a.id.itemName, SUM(a.accumulatedWeight) " +
            "FROM Accumulated a " +
            "GROUP BY a.id.itemName")
    List<Object[]> findSumWeight();
}
