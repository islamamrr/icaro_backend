package com.example.test.repository;

import com.example.test.model.CompositeKey;
import com.example.test.model.TicketWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TicketWeightRepo extends JpaRepository<TicketWeight, CompositeKey> {
    @Query("SELECT SUM(t.netWeight) FROM TicketWeight t " +
            "WHERE t.itemName = :itemName " +
            "AND ((:siteNo IS NULL AND t.id.siteNo <> 3) OR (t.id.siteNo = :siteNo))" +
            "AND STR_TO_DATE(t.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') " +
            "AND STR_TO_DATE(:endDate, '%d-%b-%y')")
    BigDecimal getTotalNetWeightByItemNameSiteNoAndCarTwoDateBetween(@Param("itemName") String itemName,
                                                                   @Param("siteNo") Integer siteNo,
                                                                   @Param("startDate") String startDate,
                                                                   @Param("endDate") String endDate);
    @Query("SELECT SUM(t.netWeight) FROM TicketWeight t " +
            "WHERE t.itemType = :itemType " +
            "AND ((:siteNo IS NULL AND t.id.siteNo <> 3) OR (t.id.siteNo = :siteNo))" +
            "AND STR_TO_DATE(t.carTwoDate, '%d-%b-%y') " +
            "BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
    BigDecimal getTotalNetWeightByItemTypeSiteNoAndCarTwoDateBetween(@Param("itemType") String itemType,
                                                                   @Param("siteNo") Integer siteNo,
                                                                   @Param("startDate") String startDate,
                                                                   @Param("endDate") String endDate);

    @Query("SELECT t FROM TicketWeight t WHERE t.id.siteNo = :siteNo AND STR_TO_DATE(t.carTwoDate, '%d-%b-%y') = STR_TO_DATE(:selectedDate, '%d-%b-%y')")
    List<TicketWeight> findAllBySiteNoAndDateBetween(Integer siteNo, String selectedDate);

    @Query("SELECT SUM(t.netWeight) FROM TicketWeight t " +
            "WHERE t.itemType = :itemType " +
            "AND t.id.siteNo = :siteNo " +
            "AND STR_TO_DATE(t.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
    BigDecimal getAllNetWeightByItemTypeAndDate(@Param("itemType") String itemType,
                                                @Param("siteNo") Integer siteNo,
                                                @Param("startDate") String startDate,
                                                @Param("endDate") String endDate);

    @Query("SELECT SUM(t.netWeight) FROM TicketWeight t " +
            "WHERE t.itemName = :itemName " +
            "AND t.id.siteNo = :siteNo " +
            "AND STR_TO_DATE(t.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
    BigDecimal getAllNetWeightByItemNameAndDate(@Param("itemName") String itemName,
                                                @Param("siteNo") Integer siteNo,
                                                @Param("startDate") String startDate,
                                                @Param("endDate") String endDate);

//    @Query("SELECT t FROM TicketWeight t WHERE t.id.siteNo = :siteNo AND t.id.ticketId = (SELECT MAX(t2.id.ticketId) FROM TicketWeight t2)")
//    TicketWeight findTicketWithMaxTicketIdBySiteNo(@Param("siteNo") Integer siteNo);

    @Query("SELECT t FROM TicketWeight t WHERE t.id.siteNo = :siteNo AND t.id.ticketId = (SELECT MAX(t2.id.ticketId) FROM TicketWeight t2 WHERE t2.id.siteNo = :siteNo)")
    TicketWeight findTicketWithMaxTicketIdBySiteNo(@Param("siteNo") Integer siteNo);

    @Query("SELECT t.carTwoDate, SUM(t.netWeight) FROM TicketWeight t " +
            "WHERE t.itemName = :itemName " +
            "AND (:siteNo IS NULL OR t.id.siteNo = :siteNo) " +
            "AND (:centerId IS NULL OR t.center.centerId = :centerId) " +
            "AND (:villageId IS NULL OR t.village.villageId = :villageId) " +
            "AND STR_TO_DATE(t.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y') " +
            "GROUP BY t.carTwoDate")
    List<Object> getDateNetWeightsMapByItemNameVillageCenter(
            @Param("itemName") String itemName,
            @Param("siteNo") Integer siteNo,
            @Param("centerId") Integer centerId,
            @Param("villageId") Integer villageId,
            @Param("startDate") String startDate,
            @Param("endDate") String endDate
    );


    @Query("SELECT t.center.centerName, SUM(t.netWeight) " +
            "FROM TicketWeight t " +
            "WHERE t.itemType = :itemType " +
            "AND t.id.siteNo NOT IN (3) " + // Exclude siteNo 3
            "AND STR_TO_DATE(t.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y') " +
            "GROUP BY t.center.centerName")
    List<Object[]> getCenterNetWeights(
            String startDate,
            String endDate,
            String itemType
    );
}