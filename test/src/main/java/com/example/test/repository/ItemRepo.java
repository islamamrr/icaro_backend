package com.example.test.repository;

import com.example.test.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Integer> {
//    @Query("SELECT i.itemName FROM Item i WHERE i.itemType = :itemType")
//    List<String> findItemNamesByItemType(@Param("itemType") String itemType);

    @Query("SELECT i.itemId, i.itemName FROM Item i WHERE i.itemType = :itemType")
    List<Object[]> getItemIdNameByItemType(@Param("itemType") String itemType);

}
