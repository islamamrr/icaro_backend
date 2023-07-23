package com.example.test.controller;

import com.example.test.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(value = {"http://localhost:63342", "http://www.dksolidwaste.com"}, allowCredentials = "true")
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/items/{itemType}/item-names")
    public Map<Integer, String> getItemNamesByItemType(@PathVariable("itemType") String itemType) {
        Map<Integer, String> itemNames = itemService.getItemIdNameMapByItemType(itemType);
        return itemNames;
    }

}
