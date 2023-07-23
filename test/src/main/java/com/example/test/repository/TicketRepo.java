//package com.example.test.repository;
//
//import com.example.test.model.Ticket;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//public interface TicketRepo extends JpaRepository<Ticket, Integer> {
//
//    List<Ticket> findByTicketIdOrderNotAndItemItemNameAndSiteNo(Integer order, String itemName, Integer siteNo);
//
//    List<Ticket> findBySiteNo(Integer siteNo);
//
//    @Query("SELECT t FROM Ticket t JOIN t.weight w WHERE t.siteNo = :siteNo AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') = STR_TO_DATE(:selectedDate, '%d-%b-%y')")
//    List<Ticket> findAllBySiteNoAndCarTwoDateBetween(Integer siteNo, String selectedDate);
//
//
//    @Query("SELECT t FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemType = :itemType AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    List<Ticket> findAllByItemTypeAndCarTwoDateBetween(String itemType, String startDate, String endDate);
//
//    @Query("SELECT t FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemName = :itemName AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    List<Ticket> findAllByItemNameAndCarTwoDateBetween(String itemName, String startDate, String endDate);
//
////    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemName = :itemName AND (:siteNo IS NULL OR t.siteNo = :siteNo) AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
////    BigDecimal getTotalNetWeightByItemNameSiteNoAndCarTwoDateBetween(@Param("itemName") String itemName, @Param("siteNo") Integer siteNo, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemId = :itemId AND (:siteNo IS NULL OR t.siteNo = :siteNo) AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    BigDecimal getTotalNetWeightByItemIdSiteNoAndCarTwoDateBetween(@Param("itemId") Integer itemId, @Param("siteNo") Integer siteNo, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//
////    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemName = :itemName AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
////    BigDecimal getTotalNetWeightByItemNameAndCarTwoDateBetween(@Param("itemName") String itemName, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemId = :itemId AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    BigDecimal getTotalNetWeightByItemIdAndCarTwoDateBetween(@Param("itemId") Integer itemId, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//
//    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemType = :itemType AND (:siteNo IS NULL OR t.siteNo = :siteNo) AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    BigDecimal getTotalNetWeightByItemTypeSiteNoAndCarTwoDateBetween(@Param("itemType") String itemType, @Param("siteNo") Integer siteNo, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemType = :itemType AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    BigDecimal getTotalNetWeightByItemTypeAndCarTwoDateBetween(@Param("itemType") String itemType, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//
//    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemType = :itemType AND t.siteNo = :siteNo AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    BigDecimal getNetWeightByItemTypeAndSiteNoAndCarTwoDateBetween(@Param("itemType") String itemType, @Param("siteNo") Integer siteNo, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
////    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemName = :itemName AND t.siteNo = :siteNo AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
////    BigDecimal getNetWeightByItemNameAndSiteNoAndCarTwoDateBetween(@Param("itemName") String itemName, @Param("siteNo") Integer siteNo, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//    @Query("SELECT SUM(w.netWeight) FROM Ticket t JOIN t.weight w WHERE t.item.itemId = :itemId AND t.siteNo = :siteNo AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y')")
//    BigDecimal getNetWeightByItemIdAndSiteNoAndCarTwoDateBetween(@Param("itemId") Integer itemId, @Param("siteNo") Integer siteNo, @Param("startDate") String startDate, @Param("endDate") String endDate);
//
//    @Query("SELECT w.carTwoDate, SUM(w.netWeight) FROM Ticket t JOIN t.weight w JOIN t.item i WHERE i.itemId = :itemId AND (:siteNo IS NULL OR t.siteNo = :siteNo) AND (:centerId IS NULL OR t.center.centerId = :centerId) AND (:villageId IS NULL OR t.village.villageId = :villageId) AND STR_TO_DATE(w.carTwoDate, '%d-%b-%y') BETWEEN STR_TO_DATE(:startDate, '%d-%b-%y') AND STR_TO_DATE(:endDate, '%d-%b-%y') GROUP BY w.carTwoDate")
//    List<Object> getNetWeightByItemIdSiteNoCenterIdVillageIdAndCarTwoDateBetween(
//            @Param("itemId") Integer itemId,
//            @Param("siteNo") Integer siteNo,
//            @Param("centerId") Integer centerId,
//            @Param("villageId") Integer villageId,
//            @Param("startDate") String startDate,
//            @Param("endDate") String endDate
//    );
//
//
//    @Query("SELECT t FROM Ticket t WHERE t.ticketIdOrder = (SELECT MAX(t2.ticketIdOrder) FROM Ticket t2)")
//    Ticket findTicketWithMaxTicketIdOrder();
//
//}