package com.example.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class AccumulatedCompositeKey implements Serializable {

    @Column(name = "SiteNo")
    private Integer siteNo;

    @Column(name = "ItemName")
    private String itemName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccumulatedCompositeKey that = (AccumulatedCompositeKey) o;
        return Objects.equals(siteNo, that.siteNo) && Objects.equals(itemName, that.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(siteNo, itemName);
    }
}
