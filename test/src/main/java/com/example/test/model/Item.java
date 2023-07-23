package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "Items")
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @Column(name = "Item_ID")
    private Integer itemId;

    @Column(name = "Item_Name")
    private String itemName;

    @Column(name = "Item_Type")
    private String itemType;

}
