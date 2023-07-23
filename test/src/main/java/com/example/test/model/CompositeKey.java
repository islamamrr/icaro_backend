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
public class CompositeKey implements Serializable {
    @Column(name = "Ticket_ID_Order")
    private Integer ticketId;

    @Column(name = "Site_ID")
    private Integer siteNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompositeKey that = (CompositeKey) o;
        return Objects.equals(ticketId, that.ticketId) && Objects.equals(siteNo, that.siteNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, siteNo);
    }
}
