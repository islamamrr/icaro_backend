package com.example.test.service;

import com.example.test.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepo;

//    public List<String> getItemNamesByItemType(String itemType) {
//        List<String> itemNames = itemRepo.findItemNamesByItemType(itemType);
//        return itemNames;
//    }

    public Map<Integer, String> getItemIdNameMapByItemType(String itemType) {
        List<Object[]> results = itemRepo.getItemIdNameByItemType(itemType);
        Map<Integer, String> itemIdNameMap = new LinkedHashMap<>();
        for (Object[] result : results) {
            Integer itemId = (Integer) result[0];
            String itemName = (String) result[1];
            itemIdNameMap.put(itemId, itemName);
        }
        return itemIdNameMap;
    }

}
